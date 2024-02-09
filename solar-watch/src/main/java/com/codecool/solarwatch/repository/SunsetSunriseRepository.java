package com.codecool.solarwatch.repository;

import com.codecool.solarwatch.model.City;
import com.codecool.solarwatch.model.SunsetSunrise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface SunsetSunriseRepository extends JpaRepository<SunsetSunrise, Long> {
    Optional<SunsetSunrise> findByCityAndDate(City city, String date);
}
