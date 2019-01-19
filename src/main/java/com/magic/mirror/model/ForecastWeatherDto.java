package com.magic.mirror.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ForecastWeatherDto {

    String temp;
    String icon;
    String dayName;
}
