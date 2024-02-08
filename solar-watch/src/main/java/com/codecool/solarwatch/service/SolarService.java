package com.codecool.solarwatch.service;

import com.codecool.solarwatch.model.GeoLocation;
import com.codecool.solarwatch.model.OpenWeatherMapApiResponse;
import com.codecool.solarwatch.model.SolarTimesResponse;
import com.codecool.solarwatch.model.SunriseSunsetApiResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

@Service
public class SolarService {
    private static final String SUNRISE_SUNSET_API_URL = "https://api.sunrise-sunset.org/json";
    private static final String OPENWEATHERMAP_API_URL = "https://api.openweathermap.org/data/2.5/weather";
    private static final String OPENWEATHERMAP_API_KEY = System.getenv("OPENWEATHERMAP_API_KEY");

    private final RestTemplate restTemplate;

    public SolarService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public SolarTimesResponse getSolarTimes(String city, LocalDate date) {
        GeoLocation cityLocation = getCityLocation(city);
        String apiUrl = String.format("%s?lat=%s&lng=%s&date=%s", SUNRISE_SUNSET_API_URL, cityLocation.getLatitude(), cityLocation.getLongitude(), date.toString());
        SunriseSunsetApiResponse sunriseSunsetApiResponse = restTemplate.getForObject(apiUrl, SunriseSunsetApiResponse.class);

        String sunrise = sunriseSunsetApiResponse.getResults().getSunrise();
        String sunset = sunriseSunsetApiResponse.getResults().getSunset();

        return new SolarTimesResponse(sunrise, sunset);
    }

    private GeoLocation getCityLocation (String cityName) {
        String apiUrl = String.format("%s?q=%s&appid=%s", OPENWEATHERMAP_API_URL, cityName, OPENWEATHERMAP_API_KEY);
        OpenWeatherMapApiResponse openWeatherMapApiResponse = restTemplate.getForObject(apiUrl, OpenWeatherMapApiResponse.class);

        Double latitude = openWeatherMapApiResponse.getCoord().getLat();
        Double longitude = openWeatherMapApiResponse.getCoord().getLon();

        return new GeoLocation(latitude, longitude);
    }
}
