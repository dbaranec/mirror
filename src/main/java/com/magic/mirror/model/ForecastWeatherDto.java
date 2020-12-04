package com.magic.mirror.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ForecastWeatherDto {

    String temp;
    String tempMin;
    String tempMax;
    String icon;
    String dayName;
    String pop;
}
