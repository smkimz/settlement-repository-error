package com.hanghae.ad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.hanghae.user", "com.hanghae.video"})
public class AdApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdApplication.class, args);
    }

}
