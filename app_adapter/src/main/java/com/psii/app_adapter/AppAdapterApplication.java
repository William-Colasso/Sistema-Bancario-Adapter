package com.psii.app_adapter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = "com.psii.app_adapter")
public class AppAdapterApplication {
    public static void main(String[] args) {
        SpringApplication.run(AppAdapterApplication.class, args);
    }
}