package com.gr.hua.dit.project2025.StreetFoodGo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class StreetFoodGoApplication {

	public static void main(String[] args) {
		SpringApplication.run(StreetFoodGoApplication.class, args);
	}

}