package com.cts.bms.applyloan.restclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "${feign.loginClientName}", url = "${feign.loginClientUrl}")
public interface LoginClient {
	@GetMapping("/validate")
	public ResponseEntity<String> validateToken(@RequestHeader("Authorization") String token);

}
