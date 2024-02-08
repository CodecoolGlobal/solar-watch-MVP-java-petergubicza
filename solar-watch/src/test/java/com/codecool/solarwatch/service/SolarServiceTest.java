package com.codecool.solarwatch.service;

import com.codecool.solarwatch.model.SolarTimesResponse;
import com.codecool.solarwatch.model.SunriseSunsetApiResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SolarServiceTest {

    @Mock
    private RestTemplate restTemplate;
}