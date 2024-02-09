package com.codecool.solarwatch.service;

import com.codecool.solarwatch.exception.InvalidCityException;
import com.codecool.solarwatch.exception.InvalidDateException;
import com.codecool.solarwatch.model.*;
import com.codecool.solarwatch.repository.CityRepository;
import com.codecool.solarwatch.repository.SunsetSunriseRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SolarService {
    private static final String SUNRISE_SUNSET_API_URL = "https://api.sunrise-sunset.org/json";
    private static final String OPENWEATHERMAP_API_URL = "https://api.openweathermap.org/data/2.5/weather";
    private static final String OPENWEATHERMAP_API_KEY = System.getenv("OPENWEATHERMAP_API_KEY");

    private final RestTemplate restTemplate;
    private final CityRepository cityRepository;
    private final SunsetSunriseRepository sunsetSunriseRepository;

    public SolarService(RestTemplate restTemplate, CityRepository cityRepository, SunsetSunriseRepository sunsetSunriseRepository) {
        this.restTemplate = restTemplate;
        this.cityRepository = cityRepository;
        this.sunsetSunriseRepository = sunsetSunriseRepository;
    }

    private City getCityByName(String cityName) {
        return cityRepository.findByName(cityName).orElse(null);
    }

    private SunsetSunrise getSunsetSunriseByCtyAndDate(City city, String date) {
        return sunsetSunriseRepository.findByCityAndDate(city, date).orElse(null);
    }

    public SolarTimesResponse getSolarTimes(String cityName, String date) {
        SolarTimesResponse solarTimesResponse = null;
        City city = getCityByName(cityName);

        if (city != null) { // if city is stored in DB
            SunsetSunrise sunsetSunrise = getSunsetSunriseByCtyAndDate(city, date);

            if (sunsetSunrise != null) { // if the sunrise/sunset is stored in DB for the date
                solarTimesResponse = new SolarTimesResponse(sunsetSunrise.getSunrise(), sunsetSunrise.getSunset());

            } else {// if only the city stored but the given date is not
                solarTimesResponse = getSolarTimesFromExternalApi(city.getLatitude(), city.getLongitude(), date);
                addSunsetSunriseToDatabase(city, date, solarTimesResponse.sunrise(), solarTimesResponse.sunset());
            }

        } else {// if DB doesn't have the city
            GeoLocation cityLocation = getCityLocationFromExternalApi(cityName);
            solarTimesResponse = getSolarTimesFromExternalApi(cityLocation.getLatitude(), cityLocation.getLongitude(), date);

            // add city and sunset/sunrise info to DB
            City newCity = new City(cityName, cityLocation.getLatitude(), cityLocation.getLongitude());
            cityRepository.save(newCity);
            addSunsetSunriseToDatabase(newCity, date, solarTimesResponse.sunrise(), solarTimesResponse.sunset());
        }

        return solarTimesResponse;
    }

    private void addSunsetSunriseToDatabase(City city, String date, String sunrise, String sunset) {
        SunsetSunrise sunsetSunrise = new SunsetSunrise(city, date, sunrise, sunset);
        sunsetSunriseRepository.save(sunsetSunrise);
    }

    private SolarTimesResponse getSolarTimesFromExternalApi(double lat, double lng, String date) {
        String apiUrl = String.format("%s?lat=%s&lng=%s&date=%s", SUNRISE_SUNSET_API_URL, lat, lng, date);
        SunriseSunsetApiResponse sunriseSunsetApiResponse;
        try {
            sunriseSunsetApiResponse = restTemplate.getForObject(apiUrl, SunriseSunsetApiResponse.class);
        } catch (Exception e) {
            throw new InvalidDateException();
        }

        String sunrise = sunriseSunsetApiResponse.getResults().getSunrise();
        String sunset = sunriseSunsetApiResponse.getResults().getSunset();

        return new SolarTimesResponse(sunrise, sunset);
    }

    private GeoLocation getCityLocationFromExternalApi(String cityName) {
        String apiUrl = String.format("%s?q=%s&appid=%s", OPENWEATHERMAP_API_URL, cityName, OPENWEATHERMAP_API_KEY);
        OpenWeatherMapApiResponse openWeatherMapApiResponse;

        try {
            openWeatherMapApiResponse = restTemplate.getForObject(apiUrl, OpenWeatherMapApiResponse.class);
        } catch (Exception e) {
            throw new InvalidCityException();
        }

        Double latitude = openWeatherMapApiResponse.getCoord().getLat();
        Double longitude = openWeatherMapApiResponse.getCoord().getLon();

        return new GeoLocation(latitude, longitude);
    }
}
