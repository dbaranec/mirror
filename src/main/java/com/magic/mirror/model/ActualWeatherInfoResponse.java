package com.magic.mirror.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActualWeatherInfoResponse {

    @JsonProperty("name")
    String city;
    Main main;


    @Getter
    public class Main {

        String temp;
        String temp_min;
        String temp_max;
    }
}
