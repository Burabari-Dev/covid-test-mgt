package com.codebase101.covidtestmgt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity
public class CovidTestMgtApplication {

	public static void main(String[] args) {
		SpringApplication.run(CovidTestMgtApplication.class, args);
	}

}
