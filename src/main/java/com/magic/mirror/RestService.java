package com.magic.mirror;

import com.magic.mirror.clients.AbalinClient;
import com.magic.mirror.clients.ForismaticClient;
import com.magic.mirror.clients.OpenWeatherClient;
import com.magic.mirror.model.ActualWeatherTodayDto;
import com.magic.mirror.model.ActualWeatherTodayResponse;
import com.magic.mirror.model.ForecastWeatherDto;
import com.magic.mirror.model.ForecastWeatherResponse;
import com.magic.mirror.model.Geolocation;
import com.magic.mirror.model.NameDayTodayDto;
import com.magic.mirror.model.NameDayTodayRequest;
import com.magic.mirror.model.NameDayTodayResponse;
import com.magic.mirror.model.QouteRequest;
import com.magic.mirror.model.QouteResponse;
import com.magic.mirror.model.WeatherParamRequest;
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

@RestController
@CrossOrigin(origins = "*")
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

        String dayNames[] = new DateFormatSymbols().getWeekdays();

        String dayOne = getDate(calendar, 1);
        String dayOneName = dayNames[calendar.get(Calendar.DAY_OF_WEEK)];

        String dayTwo = getDate(calendar, 1);
        String dayTwoName = dayNames[calendar.get(Calendar.DAY_OF_WEEK)];

        String dayThree = getDate(calendar, 1);
        String dayThreeName = dayNames[calendar.get(Calendar.DAY_OF_WEEK)];

        String dayFour = getDate(calendar, 1);
        String dayFourName = dayNames[calendar.get(Calendar.DAY_OF_WEEK)];

        List<ForecastWeatherDto> forecastWeatherDto = new ArrayList<>();

        List<ForecastWeatherResponse.ForecastWeatherInfo> forecastWeatherInfos = response.getForecastWeatherInfos();


        for (ForecastWeatherResponse.ForecastWeatherInfo info : forecastWeatherInfos) {

            String forecastTime = " 15:00:00";

            BigDecimal bd = new BigDecimal(info.getMain().getTemp());
            BigDecimal rounded = bd.setScale(0, BigDecimal.ROUND_HALF_UP);

            if (info.getDt_txt().contains(dayOne + forecastTime)) {
                forecastWeatherDto.add(ForecastWeatherDto.builder().dayName(dayOneName).icon(info.getWeathers().iterator().next().getIcon()).temp(String.valueOf(rounded)).build());
            } else if (info.getDt_txt().contains(dayTwo + forecastTime)) {
                forecastWeatherDto.add(ForecastWeatherDto.builder().dayName(dayTwoName).icon(info.getWeathers().iterator().next().getIcon()).temp(String.valueOf(rounded)).build());
            } else if (info.getDt_txt().contains(dayThree + forecastTime)) {
                forecastWeatherDto.add(ForecastWeatherDto.builder().dayName(dayThreeName).icon(info.getWeathers().iterator().next().getIcon()).temp(String.valueOf(rounded)).build());
            } else if (info.getDt_txt().contains(dayFour + forecastTime)) {
                forecastWeatherDto.add(ForecastWeatherDto.builder().dayName(dayFourName).icon(info.getWeathers().iterator().next().getIcon()).temp(String.valueOf(rounded)).build());
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
        return NameDayTodayDto.builder().nameDayToday(response.getData().iterator().next().getNameDays().getName()).build();
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
