package com.danielliao.coindesk_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CoindeskApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoindeskApiApplication.class, args);
	}

}
