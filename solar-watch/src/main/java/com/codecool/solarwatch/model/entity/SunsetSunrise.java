package com.codecool.solarwatch.model.entity;

import jakarta.persistence.*;

@Entity
public class SunsetSunrise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private City city;
    private String date;
    private String sunrise;
    private String sunset;

    public SunsetSunrise(City city, String date, String sunrise, String sunset) {
        this.city = city;
        this.date = date;
        this.sunrise = sunrise;
        this.sunset = sunset;
    }

    public SunsetSunrise() {

    }

    public City getCity() {
        return city;
    }

    public String getDate() {
        return date;
    }

    public String getSunrise() {
        return sunrise;
    }

    public String getSunset() {
        return sunset;
    }
}
