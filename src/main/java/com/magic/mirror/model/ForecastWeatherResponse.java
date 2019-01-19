package com.magic.mirror.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ForecastWeatherResponse {


    @JsonProperty("list")
    List<ForecastWeatherInfo> forecastWeatherInfos;


    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ForecastWeatherInfo {

        Main main;

        @JsonProperty("weather")
        List<Weather> weathers;


        String dt_txt;

        @Getter
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Main {

            String temp;
        }

        @Getter
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Weather {

            String icon;
        }
    }
}
