package com.magic.mirror;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableFeignClients
@ComponentScan()
public class MirrorApplication {

    public static void main(String[] args) {
        SpringApplication.run(MirrorApplication.class, args);
    }
}


