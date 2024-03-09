package com.codecool.solarwatch.controller;

import com.codecool.solarwatch.model.SolarTimesResponse;
import com.codecool.solarwatch.model.entity.SunsetSunrise;
import com.codecool.solarwatch.service.SolarService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/solar-watch")
public class SolarWatchController {

    private SolarService solarService;

    public SolarWatchController(SolarService solarService) {
        this.solarService = solarService;
    }

    @GetMapping("/solar-times")
    public SolarTimesResponse getSolarTimes(@RequestParam String cityName, @RequestParam String date) {
        return solarService.getSolarTimes(cityName, date);
    }

    @PatchMapping ("/update")
    public SunsetSunrise updateSolarTimes(@RequestParam String cityName, @RequestParam String date, @RequestParam String sunrise, @RequestParam String sunset) {
        return solarService.updateSolarTimes(cityName, date, sunrise, sunset);
    }

    @DeleteMapping ("/delete")
    public boolean deleteCity(@RequestParam String cityName, @RequestParam String date){
        return solarService.deleteSolarTimes(cityName);
    }
}
