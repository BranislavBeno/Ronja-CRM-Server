package com.ronja.crm.ronjaserver;

import com.ronja.crm.ronjaserver.config.ContainersConfig;
import org.springframework.boot.SpringApplication;

public class RonjaServerTestApplication {

    public static void main(String[] args) {
        SpringApplication.from(RonjaServerApplication::main)
                .with(ContainersConfig.class)
                .run(args);
    }
}
