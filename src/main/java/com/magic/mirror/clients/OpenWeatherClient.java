package com.magic.mirror.clients;

import com.magic.mirror.model.ActualWeatherTodayResponse;
import com.magic.mirror.model.ForecastWeatherResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(name = "OpenWeatherClient", url = "${feign.openWeather.url}")
public interface OpenWeatherClient {

    @RequestMapping(method = RequestMethod.GET, value = "/weather")
    ActualWeatherTodayResponse actualWeatherToday(@RequestParam(value = "apiKey") String apiKey,
                                                  @RequestParam(value = "lat") String lat,
                                                  @RequestParam(value = "lon") String lon,
                                                  @RequestParam(value = "units") String units);

    @RequestMapping(method = RequestMethod.GET, value = "/onecall")
    ForecastWeatherResponse forecastWeatherList(@RequestParam(value = "apiKey") String apiKey,
                                                @RequestParam(value = "lat") String lat,
                                                @RequestParam(value = "lon") String lon,
                                                @RequestParam(value = "units") String units,
                                                @RequestParam(value = "exclude") String exclude);

}
