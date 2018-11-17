package com.magic.mirror;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.magic.mirror.clients.OpenWeatherClient;
import com.magic.mirror.model.ActualWeatherRequest;
import com.magic.mirror.model.ActualWeatherResponse;

@RestController
@SpringBootApplication
@EnableFeignClients
@ComponentScan()
public class MirrorApplication {

    @Autowired
    OpenWeatherClient openWeatherClient;

    @Autowired
    ActualWeatherRequest actualWeatherRequest;

    public static void main(String[] args) {
        SpringApplication.run(MirrorApplication.class, args);
    }

    @RequestMapping("/actualWeather")
    ActualWeatherResponse getActualWeather() {
        return openWeatherClient.actualWeatherInfo(actualWeatherRequest.getApiKey(), actualWeatherRequest.getId(),
                actualWeatherRequest.getUnits());
    }
}


