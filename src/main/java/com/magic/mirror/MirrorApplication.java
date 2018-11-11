package com.magic.mirror;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.magic.mirror.clients.OpenWeatherClient;

@RestController
@SpringBootApplication
@EnableFeignClients
@ComponentScan
public class MirrorApplication {

	@Autowired
	OpenWeatherClient openWeatherClient;

	public static void main(String[] args) {
		SpringApplication.run(MirrorApplication.class, args);
	}

	@Value("${feign.openWeather.appid}")
	private String appid;

	@Value("${feign.openWeather.id}")
	private String id;

	@Value("${feign.openWeather.units}")
	private String units;

	@RequestMapping("/")
	String home() {
		return openWeatherClient.actualWeatherInfo(appid, id, units);
	}
}


