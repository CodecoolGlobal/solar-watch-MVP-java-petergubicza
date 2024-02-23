package com.codecool.solarwatch.controller;

import com.codecool.solarwatch.model.SolarTimesResponse;
import com.codecool.solarwatch.model.entity.SunsetSunrise;
import com.codecool.solarwatch.service.SolarService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/solar-watch")
public class SolarWatchController {

    private SolarService solarService;

    public SolarWatchController(SolarService solarService) {
        this.solarService = solarService;
    }

    @GetMapping("/solar-times")
    @PreAuthorize("hasRole('USER')")
    public SolarTimesResponse getSolarTimes(@RequestParam String cityName, @RequestParam String date) {
        return solarService.getSolarTimes(cityName, date);
    }

    @PatchMapping ("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public SunsetSunrise updateSolarTimes(@RequestParam String cityName, @RequestParam String date, @RequestParam String sunrise, @RequestParam String sunset) {
        return solarService.updateSolarTimes(cityName, date, sunrise, sunset);
    }

    @DeleteMapping ("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public boolean deleteCity(@RequestParam String cityName){
        return solarService.deleteSolarTimes(cityName);
    }
}
