package com.ronja.crm.ronjaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RonjaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(RonjaServerApplication.class, args);
	}
}
