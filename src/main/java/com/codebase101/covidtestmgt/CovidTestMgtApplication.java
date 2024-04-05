package com.codebase101.covidtestmgt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.codebase101.covidtestmgt.config.InitCredentials;

@SpringBootApplication
public class CovidTestMgtApplication {

	public static void main(String[] args) {
		InitCredentials.newInstance();					//-> Initialize the singleton class for all credentials
		SpringApplication.run(CovidTestMgtApplication.class, args);
	}

}
