package com.magic.mirror;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.magic.mirror.clients.AbalinClient;
import com.magic.mirror.clients.ForismaticClient;
import com.magic.mirror.clients.OpenWeatherClient;
import com.magic.mirror.model.ActualWeatherTodayRequest;
import com.magic.mirror.model.ActualWeatherTodayResponse;
import com.magic.mirror.model.GetActualWeatherTodayResponse;
import com.magic.mirror.model.GetNameDayTodayResponse;
import com.magic.mirror.model.NameDayTodayRequest;
import com.magic.mirror.model.NameDayTodayResponse;
import com.magic.mirror.model.QouteRequest;
import com.magic.mirror.model.QouteResponse;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class RestService {

    @Autowired
    private OpenWeatherClient openWeatherClient;

    @Autowired
    private AbalinClient abalinClient;

    @Autowired
    private ForismaticClient forismaticClient;

    @Autowired
    private ActualWeatherTodayRequest actualWeatherTodayRequest;

    @Autowired
    private NameDayTodayRequest nameDayTodayRequest;

    @Autowired
    private QouteRequest qouteRequest;

    @RequestMapping("/getActualWeatherToday")
    GetActualWeatherTodayResponse getActualWeather() {

        ActualWeatherTodayResponse response = openWeatherClient
                .actualWeatherToday(actualWeatherTodayRequest.getApiKey(), actualWeatherTodayRequest.getId(),
                        actualWeatherTodayRequest.getUnits());

        return GetActualWeatherTodayResponse.builder().city(response.getCity()).temp(response.getMain().getTemp())
                .icon(response.getWeathers().iterator().next().getIcon())
                .build();
    }

    @RequestMapping("/getNameDayToday")
    GetNameDayTodayResponse getNameDayToday() {

        NameDayTodayResponse response = abalinClient
                .nameDayToday(nameDayTodayRequest.getCountry(), nameDayTodayRequest.getToken());

        return GetNameDayTodayResponse.builder().nameDayToday(response.getData().getNameDayToday()).build();
    }

    @RequestMapping("/getQoute")
    QouteResponse getQoute() {

        QouteResponse qoute = forismaticClient
                .getQoute(qouteRequest.getMethod(), qouteRequest.getKey(), qouteRequest.getFormat(),
                        qouteRequest.getLang(), qouteRequest.getToken());
        if (qoute.getQuoteAuthor().isEmpty()) {
            qoute.setQuoteAuthor("Anonymous");
        }

        return qoute;
    }
}
