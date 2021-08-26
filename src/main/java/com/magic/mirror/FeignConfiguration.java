package com.magic.mirror;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.json.Jackson2CodecSupport;

import feign.Logger;
import feign.Logger.Level;
import feign.codec.Decoder;

@Configuration
public class FeignConfiguration {

    @Bean
    Logger.Level feignLoggerLevel() {
        return Level.FULL;
    }

}
