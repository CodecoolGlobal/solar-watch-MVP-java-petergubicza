package com.codecool.solarwatch.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SunriseSunsetApiResponse {

    @JsonProperty("results")
    private Results results;

    public Results getResults() {
        return results;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Results {

        @JsonProperty("sunrise")
        private String sunrise;

        @JsonProperty("sunset")
        private String sunset;

        public String getSunrise() {
            return sunrise;
        }

        public String getSunset() {
            return sunset;
        }
    }
}
