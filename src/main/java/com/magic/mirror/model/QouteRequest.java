package com.magic.mirror.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class QouteRequest {

    @Value("${feign.qoutes.method}")
    public String method;

    @Value("${feign.qoutes.key}")
    public String key;

    @Value("${feign.qoutes.format}")
    public String format;

    @Value("${feign.qoutes.lang}")
    public String lang;

    @Value("${feign.qoutes.token}")
    public String token;

}

