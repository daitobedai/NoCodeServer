package com.daitobedai.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "com.daitobedai" })
public class ApplicationStarter {

	public static void main(String args[]) {
		SpringApplication.run(ApplicationStarter.class, args);
	}
}
