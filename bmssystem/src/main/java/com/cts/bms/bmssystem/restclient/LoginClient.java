package com.cts.bms.bmssystem.restclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.cts.bms.bmssystem.model.UserLoginDetails;

@FeignClient(name = "${feign.loginClientName}", url = "${feign.loginClientUrl}")
public interface LoginClient {
	
	

		@PostMapping("/login")
		public ResponseEntity<String> login(@RequestBody UserLoginDetails userLoginDetails);

		@GetMapping("/validate")
		public ResponseEntity<String> validateToken(@RequestHeader("Authorization") String token);

	
}
