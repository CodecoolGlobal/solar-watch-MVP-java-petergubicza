package com.codecool.solarwatch.model;

public class SolarTimesResponse {

    private String sunrise;
    private String sunset;

    public SolarTimesResponse(String sunrise, String sunset) {
        this.sunrise = sunrise;
        this.sunset = sunset;
    }


    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }
}
