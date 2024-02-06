package com.codecool.solarwatch.service;

import com.codecool.solarwatch.controller.InvalidCityException;
import com.codecool.solarwatch.model.SolarTimesResponse;
import com.codecool.solarwatch.model.GeoLocation;
import com.codecool.solarwatch.model.OpenWeatherMapApiResponse;
import com.codecool.solarwatch.model.SunriseSunsetApiResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

@Service
public class SolarService {

    private static final String SUNRISE_SUNSET_API_URL = "https://api.sunrise-sunset.org/json";
    private static final String OPENWEATHERMAP_API_URL = "https://api.openweathermap.org/data/2.5/weather";
    private static final String OPENWEATHERMAP_API_KEY = "2bd0065854e1e6140188776eaf7bc834";

    public SolarTimesResponse getSolarTimes(String city, LocalDate date) {
        String apiUrl = String.format("%s?city=%s&date=%s", SUNRISE_SUNSET_API_URL, city, date.toString());
        RestTemplate restTemplate = new RestTemplate();
        SunriseSunsetApiResponse sunriseSunsetApiResponse = restTemplate.getForObject(apiUrl, SunriseSunsetApiResponse.class);

        assert sunriseSunsetApiResponse != null;
        String sunrise = sunriseSunsetApiResponse.getResults().getSunrise();
        String sunset = sunriseSunsetApiResponse.getResults().getSunset();

        return new SolarTimesResponse(sunrise, sunset);
    }

    public GeoLocation getCityLocation(String city) {

        String apiUrl = String.format("%s?q=%s&appid=%s", OPENWEATHERMAP_API_URL, city, OPENWEATHERMAP_API_KEY);
        RestTemplate restTemplate = new RestTemplate();
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
