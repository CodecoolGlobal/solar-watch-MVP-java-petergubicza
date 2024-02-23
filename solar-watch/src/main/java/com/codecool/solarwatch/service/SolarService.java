package com.codecool.solarwatch.service;

import com.codecool.solarwatch.model.*;
import com.codecool.solarwatch.model.entity.City;
import com.codecool.solarwatch.model.entity.SunsetSunrise;
import com.codecool.solarwatch.repository.CityRepository;
import com.codecool.solarwatch.repository.SunsetSunriseRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class SolarService {
    private static final String SUNRISE_SUNSET_API_URL = "https://api.sunrise-sunset.org/json";
    private static final String OPENWEATHERMAP_API_URL = "https://api.openweathermap.org/data/2.5/weather";
    private static final String OPENWEATHERMAP_API_KEY = System.getenv("OPENWEATHERMAP_API_KEY");

    private final WebClient webClient;
    private final CityRepository cityRepository;
    private final SunsetSunriseRepository sunsetSunriseRepository;

    public SolarService(WebClient webClient, CityRepository cityRepository, SunsetSunriseRepository sunsetSunriseRepository) {
        this.webClient = webClient;
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

        } else { // if DB doesn't have the city
            GeoLocation cityLocation = getCityLocationFromExternalApi(cityName);
            solarTimesResponse = getSolarTimesFromExternalApi(cityLocation.getLatitude(), cityLocation.getLongitude(), date);

            // add city and sunset/sunrise info to DB
            City newCity = new City(cityName, cityLocation.getLatitude(), cityLocation.getLongitude());
            cityRepository.save(newCity);
            addSunsetSunriseToDatabase(newCity, date, solarTimesResponse.sunrise(), solarTimesResponse.sunset());
        }

        return solarTimesResponse;
    }

    private SunsetSunrise addSunsetSunriseToDatabase(City city, String date, String sunrise, String sunset) {
        SunsetSunrise sunsetSunrise = new SunsetSunrise(city, date, sunrise, sunset);
        return sunsetSunriseRepository.save(sunsetSunrise);
    }

    private SolarTimesResponse getSolarTimesFromExternalApi(double lat, double lng, String date) {
        String apiUrl = String.format("%s?lat=%s&lng=%s&date=%s", SUNRISE_SUNSET_API_URL, lat, lng, date);
        return webClient.get()
                .uri(apiUrl)
                .retrieve()
                .bodyToMono(SunriseSunsetApiResponse.class)
                .map(sunriseSunsetApiResponse -> new SolarTimesResponse(sunriseSunsetApiResponse.getResults().getSunrise(), sunriseSunsetApiResponse.getResults().getSunset()))
                .block();
    }

    private GeoLocation getCityLocationFromExternalApi(String cityName) {
        String apiUrl = String.format("%s?q=%s&appid=%s", OPENWEATHERMAP_API_URL, cityName, OPENWEATHERMAP_API_KEY);
        return webClient.get()
                .uri(apiUrl)
                .retrieve()
                .bodyToMono(OpenWeatherMapApiResponse.class)
                .map(openWeatherMapApiResponse -> new GeoLocation(openWeatherMapApiResponse.getCoord().getLat(), openWeatherMapApiResponse.getCoord().getLon()))
                .block();
    }

    public SunsetSunrise updateSolarTimes(String cityName, String date, String sunrise, String sunset) {
        City city = getCityByName(cityName);
        return addSunsetSunriseToDatabase(city, date, sunrise, sunset);
    }

    public boolean deleteSolarTimes(String cityName) {
        City city = getCityByName(cityName);
        return sunsetSunriseRepository.deleteByCity(city);
    }
}
