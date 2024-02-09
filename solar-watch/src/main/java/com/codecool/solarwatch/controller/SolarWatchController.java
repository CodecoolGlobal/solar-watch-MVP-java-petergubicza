package com.codecool.solarwatch.controller;

import com.codecool.solarwatch.model.SolarTimesResponse;
import com.codecool.solarwatch.service.SolarService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class SolarWatchController {

    private SolarService solarService;

    public SolarWatchController(SolarService solarService) {
        this.solarService = solarService;
    }

    @GetMapping("/solar-times")
    public SolarTimesResponse getSunrise(@RequestParam String city, @RequestParam String date) {
        System.out.println(solarService.getSolarTimes(city, date));
        return solarService.getSolarTimes(city, date);
    }
}
