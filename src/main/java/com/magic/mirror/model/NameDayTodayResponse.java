package com.magic.mirror.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class NameDayTodayResponse {

    List<Data> data;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Data {

        @JsonProperty("namedays")
        NameDays nameDays;

        @Getter
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class NameDays {

            @JsonProperty("sk")
            String name;
        }
    }
}
