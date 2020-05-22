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
    Wind wind;

    @JsonProperty("weather")
    List<Weather> weathers;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class Main {

        String temp;
    }

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Weather {

        String icon;
    }

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class Wind {
        Double speed;
        Integer deg;
    }

}
