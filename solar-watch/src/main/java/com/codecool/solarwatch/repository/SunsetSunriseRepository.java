package com.codecool.solarwatch.repository;

import com.codecool.solarwatch.model.entity.City;
import com.codecool.solarwatch.model.entity.SunsetSunrise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SunsetSunriseRepository extends JpaRepository<SunsetSunrise, Long> {
    Optional<SunsetSunrise> findByCityAndDate(City city, String date);
    boolean deleteByCity(City city);
}
