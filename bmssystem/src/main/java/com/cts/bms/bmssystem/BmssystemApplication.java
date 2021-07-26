package com.cts.bms.bmssystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class BmssystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(BmssystemApplication.class, args);
	}

}
