package com.cts.bms.applyloan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class ApplyloanApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApplyloanApplication.class, args);
	}

}
