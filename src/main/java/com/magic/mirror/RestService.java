package com.magic.mirror;

import com.magic.mirror.model.ForecastWeatherDto;
import com.magic.mirror.model.ForecastWeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.magic.mirror.clients.AbalinClient;
import com.magic.mirror.clients.ForismaticClient;
import com.magic.mirror.clients.OpenWeatherClient;
import com.magic.mirror.model.WeatherParamRequest;
import com.magic.mirror.model.ActualWeatherTodayResponse;
import com.magic.mirror.model.ActualWeatherTodayDto;
import com.magic.mirror.model.NameDayTodayDto;
import com.magic.mirror.model.NameDayTodayRequest;
import com.magic.mirror.model.NameDayTodayResponse;
import com.magic.mirror.model.QouteRequest;
import com.magic.mirror.model.QouteResponse;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

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
    private WeatherParamRequest weatherParamRequest;

    @Autowired
    private NameDayTodayRequest nameDayTodayRequest;

    @Autowired
    private QouteRequest qouteRequest;

    @RequestMapping("/getActualWeatherToday")
    ActualWeatherTodayDto getActualWeather() {

        ActualWeatherTodayResponse response = openWeatherClient
                .actualWeatherToday(weatherParamRequest.getApiKey(), weatherParamRequest.getId(),
                        weatherParamRequest.getUnits());

        return ActualWeatherTodayDto.builder().city(response.getCity()).temp(response.getMain().getTemp())
                .icon(response.getWeathers().iterator().next().getIcon())
                .build();
    }

    @RequestMapping("/getForecastWeather")
    ForecastWeatherDto getForecastWeather() {
        ForecastWeatherResponse response = openWeatherClient
                .forecastWeatherList(weatherParamRequest.getApiKey(), weatherParamRequest.getId(),
                        weatherParamRequest.getUnits());

        String todayDateAndTime = response.getForecastWeatherInfos().iterator().next().getDt_txt();
        String[] tokens = todayDateAndTime.split(" ");
        String[] splits = tokens[0].split("-");

        Calendar calendar = new GregorianCalendar();
        calendar.set(Integer.valueOf(splits[0]), Integer.valueOf(splits[1]) - 1, Integer.valueOf(splits[2]));

        String dayNames[] = new DateFormatSymbols().getWeekdays();

        String dayOne = getDate(calendar , 1);
        String dayOneName = dayNames[calendar.get(Calendar.DAY_OF_WEEK)];

        String dayTwo = getDate(calendar , 1);
        String dayTwoName = dayNames[calendar.get(Calendar.DAY_OF_WEEK)];

        String dayThree = getDate(calendar , 1);
        String dayThreeName = dayNames[calendar.get(Calendar.DAY_OF_WEEK)];

        String dayFour = getDate(calendar , 1);
        String dayFourName = dayNames[calendar.get(Calendar.DAY_OF_WEEK)];

        ForecastWeatherDto forecastWeatherDto = ForecastWeatherDto.builder().build();
        List<ForecastWeatherDto.ForecastInfor> forecastInforList = new ArrayList<>();

        List<ForecastWeatherResponse.ForecastWeatherInfo> forecastWeatherInfos = response.getForecastWeatherInfos();
        for (ForecastWeatherResponse.ForecastWeatherInfo info : forecastWeatherInfos) {

            String forecastTime = " 15:00:00";
            if (info.getDt_txt().contains(dayOne + forecastTime)) {
                forecastInforList.add(ForecastWeatherDto.ForecastInfor.builder().dayName(dayOneName).icon(info.getWeathers().iterator().next().getIcon()).temp(info.getMain().getTemp()).build());
            } else if (info.getDt_txt().contains(dayTwo + forecastTime)) {
                forecastInforList.add(ForecastWeatherDto.ForecastInfor.builder().dayName(dayTwoName).icon(info.getWeathers().iterator().next().getIcon()).temp(info.getMain().getTemp()).build());
            } else if (info.getDt_txt().contains(dayThree + forecastTime)) {
                forecastInforList.add(ForecastWeatherDto.ForecastInfor.builder().dayName(dayThreeName).icon(info.getWeathers().iterator().next().getIcon()).temp(info.getMain().getTemp()).build());
            } else if (info.getDt_txt().contains(dayFour + forecastTime)) {
                forecastInforList.add(ForecastWeatherDto.ForecastInfor.builder().dayName(dayFourName).icon(info.getWeathers().iterator().next().getIcon()).temp(info.getMain().getTemp()).build());
            }
        }

        forecastWeatherDto.setForecastInfors(forecastInforList);

        return forecastWeatherDto;
    }

    private String getDate(Calendar calendar, int addDay) {
        calendar.add(Calendar.DAY_OF_MONTH, addDay);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date unformatedDayOne = calendar.getTime();
        return df.format(unformatedDayOne);
    }

    @RequestMapping("/getNameDayToday")
    NameDayTodayDto getNameDayToday() {

        NameDayTodayResponse response = abalinClient
                .nameDayToday(nameDayTodayRequest.getCountry(), nameDayTodayRequest.getToken());

        return NameDayTodayDto.builder().nameDayToday(response.getData().getNameDayToday()).build();
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
