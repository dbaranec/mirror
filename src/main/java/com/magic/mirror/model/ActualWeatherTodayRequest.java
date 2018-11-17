package com.magic.mirror.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class ActualWeatherTodayRequest {

    @Value("${feign.openWeather.apiKey}")
    public String apiKey;

    @Value("${feign.openWeather.id}")
    public String id;

    @Value("${feign.openWeather.units}")
    public String units;

}

