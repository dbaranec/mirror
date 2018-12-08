package com.magic.mirror.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GetActualWeatherTodayResponse {

    String city;
    String temp;
    String icon;
}
