package com.cts.bms.applyloan.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cts.bms.applyloan.ApplyloanApplication;

import com.cts.bms.applyloan.model.Loan;
import com.cts.bms.applyloan.service.LoanDao;


@RestController
@RequestMapping("/apply_loan")
public class LoanController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ApplyloanApplication.class);

	@Autowired
	private LoanDao loanService;
	@GetMapping("/get_loans")
	public void getAllLoans(@RequestParam Integer accountId) {
		LOGGER.info("Loans of accountId:"+accountId);
		loanService.getAllLoansByAccountId(accountId);
	}
	
	@GetMapping("/get_account_loans")
	public ResponseEntity<List<Loan>> getAllLoansByAccountId(@RequestHeader("Authorization") String token,@RequestParam Integer accountId) {
		if (!loanService.validateToken(token)) {
			LOGGER.info("End - Unauthorized");
			return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
		}
		
		LOGGER.info("Loans of accountId:"+accountId);
		
		return new ResponseEntity<List<Loan>>(loanService.getAllLoansByAccountId(accountId),HttpStatus.OK);
	}
	@PostMapping("/save_loan_details")
	public ResponseEntity<Void> saveLoanDetails(@RequestHeader("Authorization") String token,@RequestBody Loan loanDetails) {
		if (!loanService.validateToken(token)) {
			LOGGER.info("Unauthorized");
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		
		LOGGER.info("Loan details saving");
		loanService.saveLoanDetails(loanDetails);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
		

}
