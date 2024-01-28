package com.codecool.solarwatch.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherMapApiResponse {

    @JsonProperty("coord")
    private Coord coord;

    public Coord getCoord() {
        return coord;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Coord {

        @JsonProperty("lat")
        private Double lat;

        @JsonProperty("lon")
        private Double lon;

        public Double getLat() {
            return lat;
        }

        public Double getLon() {
            return lon;
        }

    }
}