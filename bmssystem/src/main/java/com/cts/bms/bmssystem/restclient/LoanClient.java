package com.cts.bms.bmssystem.restclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "${feign.loanClientName}", url = "${feign.loanClientUrl}")
public interface LoanClient {
	@GetMapping("/get_loans")
		public void  getAllLoans(@RequestParam Integer accountId);
	
}
