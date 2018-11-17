package com.magic.mirror;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.magic.mirror.clients.AbalinClient;
import com.magic.mirror.clients.OpenWeatherClient;
import com.magic.mirror.clients.ForismaticClient;
import com.magic.mirror.model.ActualWeatherTodayRequest;
import com.magic.mirror.model.ActualWeatherTodayResponse;
import com.magic.mirror.model.NameDayTodayRequest;
import com.magic.mirror.model.NameDayTodayResponse;
import com.magic.mirror.model.QouteRequest;
import com.magic.mirror.model.QouteResponse;

@RestController
public class RestService {

    @Autowired
    OpenWeatherClient openWeatherClient;

    @Autowired
    AbalinClient abalinClient;

    @Autowired
    ForismaticClient forismaticClient;

    @Autowired
    ActualWeatherTodayRequest actualWeatherTodayRequest;

    @Autowired
    NameDayTodayRequest nameDayTodayRequest;

    @Autowired
    QouteRequest qouteRequest;

    @RequestMapping("/getActualWeatherToday")
    ActualWeatherTodayResponse getActualWeather() {
        return openWeatherClient
                .actualWeatherToday(actualWeatherTodayRequest.getApiKey(), actualWeatherTodayRequest.getId(),
                        actualWeatherTodayRequest.getUnits());
    }

    @RequestMapping("/getNameDayToday")
    NameDayTodayResponse getNameDayToday() {
        return abalinClient.nameDayToday(nameDayTodayRequest.getCountry(),nameDayTodayRequest.getToken());
    }

    @RequestMapping("/getQoute")
    QouteResponse getQoute() {
        return forismaticClient.getQoute(qouteRequest.getMethod(), qouteRequest.getKey(), qouteRequest.getFormat(),
                qouteRequest.getLang(), qouteRequest.getToken());
    }
}
