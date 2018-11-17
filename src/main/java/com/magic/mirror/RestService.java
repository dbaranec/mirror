package com.magic.mirror;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.magic.mirror.clients.OpenWeatherClient;
import com.magic.mirror.model.ActualWeatherInfoRequest;
import com.magic.mirror.model.ActualWeatherInfoResponse;

@RestController
public class RestService {

    @Autowired
    OpenWeatherClient openWeatherClient;

    @Autowired
    ActualWeatherInfoRequest actualWeatherInfoRequest;

    @RequestMapping("/actualWeather")
    ActualWeatherInfoResponse getActualWeather() {
        return openWeatherClient.actualWeatherInfo(actualWeatherInfoRequest.getApiKey(), actualWeatherInfoRequest.getId(),
                actualWeatherInfoRequest.getUnits());
    }
}
