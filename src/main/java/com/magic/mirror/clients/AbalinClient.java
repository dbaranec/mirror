package com.magic.mirror.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.magic.mirror.model.NameDayTodayResponse;

import feign.Headers;

@FeignClient(name = "AbalinClient", url = "${feign.nameDay.url}")
public interface AbalinClient {

    @RequestMapping(method = RequestMethod.GET, value = "/today")
    NameDayTodayResponse nameDayToday(@RequestParam(value = "country") String country, @RequestHeader("User-Agent") String token);
}

