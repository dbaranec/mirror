package com.magic.mirror;

import com.magic.mirror.clients.AbalinClient;
import com.magic.mirror.clients.OpenWeatherClient;
import com.magic.mirror.model.ActualWeatherTodayDto;
import com.magic.mirror.model.ActualWeatherTodayResponse;
import com.magic.mirror.model.ForecastWeatherDto;
import com.magic.mirror.model.ForecastWeatherResponse;
import com.magic.mirror.model.Geolocation;
import com.magic.mirror.model.NameDayTodayDto;
import com.magic.mirror.model.NameDayTodayRequest;
import com.magic.mirror.model.NameDayTodayResponse;
import com.magic.mirror.model.WeatherParamRequest;
import com.magic.mirror.model.rest.QouteResponseOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

@RestController
@CrossOrigin(origins = "*")
public class RestService {

    @Autowired
    private OpenWeatherClient openWeatherClient;

    @Autowired
    private AbalinClient abalinClient;

    @Autowired
    private WeatherParamRequest weatherParamRequest;

    @Autowired
    private NameDayTodayRequest nameDayTodayRequest;

    @Autowired
    private MirrorManager mirrorManager;

    @RequestMapping(value = "/getActualWeatherToday", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ActualWeatherTodayDto getActualWeather(@RequestBody Geolocation geolocation) {

        ActualWeatherTodayResponse response = openWeatherClient
                .actualWeatherToday(
                        weatherParamRequest.getApiKey(),
                        geolocation.getLat(),
                        geolocation.getLon(),
                        weatherParamRequest.getUnits());

        BigDecimal bd = new BigDecimal(response.getMain().getTemp());
        BigDecimal roundedTemp = bd.setScale(0, BigDecimal.ROUND_HALF_UP);

        return ActualWeatherTodayDto.builder().city(response.getCity()).temp(roundedTemp.toString())
                .icon(response.getWeathers().iterator().next().getIcon())
                .build();
    }

    @RequestMapping(value = "/getForecastWeather", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    List<ForecastWeatherDto> getForecastWeather(@RequestBody Geolocation geolocation) {
        ForecastWeatherResponse response = openWeatherClient
                .forecastWeatherList(
                        weatherParamRequest.getApiKey(),
                        geolocation.getLat(),
                        geolocation.getLon(),
                        weatherParamRequest.getUnits());

        String todayDateAndTime = response.getForecastWeatherInfos().iterator().next().getDt_txt();
        String[] tokens = todayDateAndTime.split(" ");
        String[] splits = tokens[0].split("-");

        Calendar calendar = new GregorianCalendar();
        calendar.set(Integer.valueOf(splits[0]), Integer.valueOf(splits[1]) - 1, Integer.valueOf(splits[2]));

        Locale id = new Locale("sk", "SK");
        DateFormatSymbols dateFormatSymbols = new DateFormatSymbols(id);
        String dayNames[] = dateFormatSymbols.getWeekdays();
        String nextDay = getDate(calendar, 1);
        List<ForecastWeatherResponse.ForecastWeatherInfo> forecastWeatherInfos = response.getForecastWeatherInfos();
        List<ForecastWeatherDto> forecastWeatherDto = new ArrayList<>();
        for (ForecastWeatherResponse.ForecastWeatherInfo info : forecastWeatherInfos) {

            String forecastTime = " 15:00:00";
            BigDecimal bd = new BigDecimal(info.getMain().getTemp());
            BigDecimal rounded = bd.setScale(0, BigDecimal.ROUND_HALF_UP);
            if (info.getDt_txt().contains(nextDay + forecastTime)) {
                forecastWeatherDto.add(ForecastWeatherDto.builder().dayName(dayNames[calendar.get(Calendar.DAY_OF_WEEK)]).icon(info.getWeathers().iterator().next().getIcon()).temp(String.valueOf(rounded)).build());
                nextDay = getDate(calendar, 1);
            }
        }

        return forecastWeatherDto;
    }

    private String getDate(Calendar calendar, int addDay) {
        calendar.add(Calendar.DAY_OF_MONTH, addDay);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date unformatedDayOne = calendar.getTime();
        return df.format(unformatedDayOne);
    }

    @RequestMapping(value = "/getNameDayToday", method = RequestMethod.GET, produces =
            "application/json")
    NameDayTodayDto getNameDayToday() {

        NameDayTodayResponse response = abalinClient
                .nameDayToday(nameDayTodayRequest.getCountry(), nameDayTodayRequest.getToken());
        return NameDayTodayDto.builder().nameDayToday(response.getData().getNameDays().getName()).build();
    }

    @RequestMapping("/getQoute")
    QouteResponseOut getQoute() {
        return mirrorManager.processQuote();
    }
}
