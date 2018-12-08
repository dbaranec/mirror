package com.magic.mirror.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class NameDayTodayResponse {

    Data data;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class Data {

        @JsonProperty("name_sk")
        String nameDayToday;
    }
}
