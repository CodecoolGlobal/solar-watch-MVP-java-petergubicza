package com.codecool.solarwatch.controller;

import com.codecool.solarwatch.model.GeoLocation;
import com.codecool.solarwatch.model.SolarTimesResponse;
import com.codecool.solarwatch.service.SolarService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class SolarWatchController {

    private final SolarService solarService;

    public SolarWatchController(SolarService solarService) {
        this.solarService = solarService;
    }

    @GetMapping("/solar-times")
    public SolarTimesResponse getSolarTimes(@RequestParam String city, @RequestParam String date) {
        return solarService.getSolarTimes(city, LocalDate.parse(date));
    }

    @GetMapping("/city-location")
    public GeoLocation getCityLocation(@RequestParam String city) {
        return solarService.getCityLocation(city);
    }
}
