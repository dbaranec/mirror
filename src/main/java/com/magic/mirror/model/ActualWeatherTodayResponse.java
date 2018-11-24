package com.magic.mirror.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActualWeatherTodayResponse {

    @JsonProperty("name")
    String city;
    Main main;
    List<Weather> weather;

    @Getter
    public class Main {

        String temp;
    }

    @Getter
    public static class Weather {

        String description;
    }
}
