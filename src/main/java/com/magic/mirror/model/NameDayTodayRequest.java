package com.magic.mirror.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class NameDayTodayRequest {

    @Value("${feign.nameDay.country}")
    public String country;

}

