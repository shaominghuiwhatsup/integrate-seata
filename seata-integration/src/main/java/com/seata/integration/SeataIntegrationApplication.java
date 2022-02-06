package com.seata.integration;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@MapperScan("com.seata.integration.mapper")
public class SeataIntegrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeataIntegrationApplication.class, args);
	}

}
