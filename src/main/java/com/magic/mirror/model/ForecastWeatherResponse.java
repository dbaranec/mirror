package com.magic.mirror.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ForecastWeatherResponse {


    @JsonProperty("daily")
    List<ForecastWeatherInfo> forecastWeatherInfos;


    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ForecastWeatherInfo {

        @JsonProperty("temp")
        Main main;

        @JsonProperty("weather")
        List<Weather> weathers;


        @JsonProperty("dt")
        String dt_txt;

        String pop;

        @Getter
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Main {

            @JsonProperty("day")
            String temp;
            String min;
            String max;
        }

        @Getter
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Weather {

            String icon;
        }
    }
}
