package com.magic.mirror.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class QouteRequest {

    @Value("${feign.qoutes.language_code}")
    public String languageCode;
}

