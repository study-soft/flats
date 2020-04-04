package com.ay.flats;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Service {

    public static void main(String[] args) {
        SpringApplication.run(Service.class, args);
    }

}
