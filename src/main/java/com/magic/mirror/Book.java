package com.magic.mirror;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name="KanbanClient", url= "http://api.openweathermap.org/data/2.5/weather?appid=1e96dde2ea8dafa8adb0446b7294bbe4&q=Ziar nad Hronom,SK&units=metric")
public interface Book {

    @RequestMapping(method = RequestMethod.GET, value = "/")
    String basic();
}
