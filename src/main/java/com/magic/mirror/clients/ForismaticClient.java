package com.magic.mirror.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.magic.mirror.model.QouteResponse;


@FeignClient(name = "QouteCliet", url = "${feign.qoutes.url}")
public interface ForismaticClient {

    @RequestMapping(method = RequestMethod.GET, value = "/weather")
    QouteResponse getQoute(@RequestParam(value = "method") String method,
            @RequestParam(value = "key") String key, @RequestParam(value = "format") String format,
            @RequestParam(value = "lang") String lang, @RequestHeader("User-Agent") String token);

}
