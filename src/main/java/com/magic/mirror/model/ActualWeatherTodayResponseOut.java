package com.magic.mirror.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ActualWeatherTodayResponseOut {

    String city;
    String temp;
    String icon;
    Double windSpeed;
    Integer windDirection;
}
