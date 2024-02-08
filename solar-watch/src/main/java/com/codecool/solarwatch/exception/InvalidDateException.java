package com.codecool.solarwatch.exception;

public class InvalidDateException extends RuntimeException{
    public InvalidDateException() {
        super("Date must be in the past (YYYY-MM-DD)");
    }
}
