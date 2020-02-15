package com.magic.mirror.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.magic.mirror.model.QouteResponse;


@FeignClient(name = "QouteCliet", url = "${feign.qoutes.url}")
public interface QouteClient {

    @RequestMapping(method = RequestMethod.GET, value = "/random")
    QouteResponse getQoute(@RequestParam(value = "language_code") String languageCode);

}
