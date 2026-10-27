package com.dohieuhien.shop.trainingsport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients
public class TrainingSportApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrainingSportApplication.class, args);
    }

}
