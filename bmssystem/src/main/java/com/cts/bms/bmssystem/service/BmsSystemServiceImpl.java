package com.cts.bms.bmssystem.service;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import com.cts.bms.bmssystem.kafkaconfig.ApplyLoanProducer;
import com.cts.bms.bmssystem.kafkaconfig.CustomerProducer;
import com.cts.bms.bmssystem.model.Customer;
import com.cts.bms.bmssystem.model.Loan;
import com.cts.bms.bmssystem.model.UserLoginDetails;
import com.cts.bms.bmssystem.restclient.LoanClient;
import com.cts.bms.bmssystem.restclient.LoginClient;

@Component
public class BmsSystemServiceImpl implements BmsSystemService {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(BmsSystemServiceImpl.class);
	@Autowired
	private CustomerProducer customerProducer;

	@Autowired
	private ApplyLoanProducer applyLoanProducer;

	@Autowired
	LoginClient loginClient;

	@Autowired
	LoanClient loanClient;
	
	List<Loan> globalLoanList;
	
	String globalMessage;

	@Override
	public void registerCustomer(String topic, Customer customer) {
		logger.info("Register");
		customerProducer.send(topic, customer);

		
	}

	@Override
	public void updateCustomer(String topic, Customer customer) {
		logger.info("Update");
		customerProducer.send(topic, customer);

		
	}

	@Override
	public void retrieveLoans(Integer accountId) {
		logger.info("Loans");
		loanClient.getAllLoans(accountId);
		
	}
	@Override
	public void applyLoan(String topic, Loan loan) {
		logger.info("Apply Loan");
		System.out.println(loan.getLoanType());
		applyLoanProducer.send(topic, loan);
		
	}

	

	@Override
	public boolean login(UserLoginDetails userLoginDetails, HttpSession session, ModelMap warning) {
		try {
			ResponseEntity<String> response = loginClient.login(userLoginDetails);
			session.setAttribute("TOKEN", "Bearer " + response.getBody());
			System.out.println((String)session.getAttribute("TOKEN"));
			System.out.println("Bearer " + response.getBody());
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			if (e.getClass().toString().contains("feign.RetryableException"))
				warning.addAttribute("loginMessage", "Server is down. Please try later.");

			else
				warning.addAttribute("loginMessage", "Unable to login. Please check your credentials.");

			return false;

		}
	}



	@Override
	public void saveLoans(List<Loan> loanList) {
		globalLoanList = loanList;
		
	}

	@Override
	public List<Loan> returnLoans() {
		LocalTime startTime = LocalTime.now();
		while ((globalLoanList == null || globalLoanList.size() == 0)
				&& startTime.until(LocalTime.now(), ChronoUnit.SECONDS) < 3)
			;
		List<Loan> loanList = globalLoanList;
		globalLoanList = null;
		return loanList;
	}

	@Override
	public boolean validateToken(String token) {
		try {
			if (token == null)
				return false;
			HttpStatus loginStatusCode = loginClient.validateToken(token).getStatusCode();
			if (loginStatusCode.equals(HttpStatus.OK)) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
	


	@Override
	public void saveMessage(String message) {
		globalMessage=message;
		
	}

	@Override
	public String getMessage() {
		LocalTime startTime = LocalTime.now();
		while ((globalMessage == null)
				&& startTime.until(LocalTime.now(), ChronoUnit.SECONDS) < 3)
			;
		String message=globalMessage;
		globalMessage = null;
		return message;
		
	}

}
