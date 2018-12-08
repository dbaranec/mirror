package com.magic.mirror.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActualWeatherTodayResponse {

    @JsonProperty("name")
    String city;

    Main main;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class Main {

        String temp;
    }

}
