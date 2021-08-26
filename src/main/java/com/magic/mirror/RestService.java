package com.magic.mirror;

import com.magic.mirror.clients.AbalinClient;
import com.magic.mirror.clients.OpenWeatherClient;
import com.magic.mirror.model.ActualWeatherTodayResponseOut;
import com.magic.mirror.model.ForecastWeatherDto;
import com.magic.mirror.model.ForecastWeatherResponse;
import com.magic.mirror.model.Geolocation;
import com.magic.mirror.model.NameDayTodayDto;
import com.magic.mirror.model.NameDayTodayRequest;
import com.magic.mirror.model.NameDayTodayResponse;
import com.magic.mirror.model.WeatherParamRequest;
import com.magic.mirror.model.rest.QouteResponseOut;
import com.magic.mirror.model.rest.RssFeedsOut;
import com.sun.syndication.io.FeedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormatSymbols;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
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

    @Autowired
    private RSSFeedReader rssFeedReader;

    @RequestMapping(value = "/getActualWeatherToday", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ActualWeatherTodayResponseOut getActualWeather(@RequestBody Geolocation geolocation) {
        return mirrorManager.processActualWeather(geolocation);
    }

    @RequestMapping(value = "/getForecastWeather", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    List<ForecastWeatherDto> getForecastWeather(@RequestBody Geolocation geolocation) {
        ForecastWeatherResponse response = openWeatherClient
                .forecastWeatherList(
                        weatherParamRequest.getApiKey(),
                        geolocation.getLat(),
                        geolocation.getLon(),
                        weatherParamRequest.getUnits(),
                        weatherParamRequest.getExclude());

        String todayDateAndTime = response.getForecastWeatherInfos().iterator().next().getDt_txt();
        LocalDate localDate =
                Instant.ofEpochMilli(Long.valueOf(todayDateAndTime) * 1000).atZone(ZoneId.systemDefault()).toLocalDate();

        Calendar calendar = new GregorianCalendar();
        GregorianCalendar.from(localDate.atStartOfDay(ZoneId.systemDefault()));

        Locale id = new Locale("sk", "SK");
        DateFormatSymbols dateFormatSymbols = new DateFormatSymbols(id);
        String dayNames[] = dateFormatSymbols.getWeekdays();
        List<ForecastWeatherResponse.ForecastWeatherInfo> forecastWeatherInfos = response.getForecastWeatherInfos();

        //remove actual day from list
        /*forecastWeatherInfos.remove(0);
        calendar.add(Calendar.DAY_OF_MONTH, 1);*/

        List<ForecastWeatherDto> forecastWeatherDto = new ArrayList<>();
        for (ForecastWeatherResponse.ForecastWeatherInfo info : forecastWeatherInfos) {

            ForecastWeatherDto newForecastWeatherDto = new ForecastWeatherDto();
            newForecastWeatherDto.setDayName(dayNames[calendar.get(Calendar.DAY_OF_WEEK)]);
            newForecastWeatherDto.setIcon(info.getWeathers().iterator().next().getIcon());
            newForecastWeatherDto.setPop(info.getPop());

            BigDecimal temp = new BigDecimal(info.getMain().getTemp());
            BigDecimal roundedTemp = temp.setScale(0, BigDecimal.ROUND_HALF_UP);
            newForecastWeatherDto.setTemp(String.valueOf(roundedTemp));

            BigDecimal tempMin = new BigDecimal(info.getMain().getMin());
            BigDecimal roundedTempMin = tempMin.setScale(0, BigDecimal.ROUND_HALF_UP);
            newForecastWeatherDto.setTempMin(String.valueOf(roundedTempMin));

            BigDecimal tempMax = new BigDecimal(info.getMain().getMax());
            BigDecimal roundedTempMax = tempMax.setScale(0, BigDecimal.ROUND_HALF_UP);
            newForecastWeatherDto.setTempMax(String.valueOf(roundedTempMax));

            forecastWeatherDto.add(newForecastWeatherDto);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        return forecastWeatherDto;
    }

    @RequestMapping(value = "/getNameDayToday", method = RequestMethod.GET, produces =
            "application/json")
    NameDayTodayDto getNameDayToday() {

        NameDayTodayResponse response = abalinClient
                .nameDayToday(nameDayTodayRequest.getCountry());
        return NameDayTodayDto.builder().nameDayToday(response.getData().getNameDays().getName()).build();
    }

    @RequestMapping("/getQoute")
    QouteResponseOut getQoute() {
        return mirrorManager.processQuote();
    }

    @RequestMapping("/rss")
    public RssFeedsOut getRss() throws IOException, FeedException {
        return rssFeedReader.readRss();
    }

    private LocalDate convertToLocalDateViaMilisecond(Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}
