package com.codecool.solarwatch.controller;

public class InvalidCityException extends RuntimeException {
    public InvalidCityException() {
        super("City does NOT exist");
    }
}

