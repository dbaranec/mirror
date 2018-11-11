package com.magic.mirror;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
@EnableFeignClients
public class MirrorApplication {

	@Autowired
	Book book;

	public static void main(String[] args) {
		SpringApplication.run(MirrorApplication.class, args);
	}

	@RequestMapping("/")
	String home() {
		return book.basic();
	}

}


