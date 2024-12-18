package com.numpok;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@ConfigurationPropertiesScan(basePackages = "com.numpok.config")
@EnableConfigurationProperties
public class NumberPokerApplication {

	public static void main(String[] args) {
		SpringApplication.run(NumberPokerApplication.class, args);
	}

}
