package com.trendapp.appv01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling  // Zamanlayıcıları etkinleştir
public class Trendappv01Application {
    public static void main(String[] args) {
        SpringApplication.run(Trendappv01Application.class, args);
    }
}
