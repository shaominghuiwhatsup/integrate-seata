package com.seata.integration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SeataIntegrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeataIntegrationApplication.class, args);
	}

}
