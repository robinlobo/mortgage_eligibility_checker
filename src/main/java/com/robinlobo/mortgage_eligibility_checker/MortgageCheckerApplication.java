package com.robinlobo.mortgage_eligibility_checker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MortgageCheckerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MortgageCheckerApplication.class, args);
	}

}
