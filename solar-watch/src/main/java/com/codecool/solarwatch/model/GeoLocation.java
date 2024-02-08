package com.codecool.solarwatch.model;

public class GeoLocation {

    private Double latitude;
    private Double longitude;

    public GeoLocation(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
}

