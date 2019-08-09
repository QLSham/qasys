package com.ps.qa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @description: App
 * @author: ql-xrh
 * @create: 2019-07-18
 */
@SpringBootApplication
@EnableScheduling
public class MarketApp {
    public static void main(String[] args) {
        SpringApplication.run(MarketApp.class,args);
    }
}
