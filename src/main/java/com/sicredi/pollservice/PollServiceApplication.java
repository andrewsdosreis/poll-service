package com.sicredi.pollservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PollServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PollServiceApplication.class, args);
	}

}
