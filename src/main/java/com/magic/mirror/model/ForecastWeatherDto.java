package com.magic.mirror.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ForecastWeatherDto {

    List<ForecastInfor> forecastInfors;

    @Getter
    @Setter
    @Builder
    public static class ForecastInfor {
        String temp;
        String icon;
        String dayName;
    }
}
