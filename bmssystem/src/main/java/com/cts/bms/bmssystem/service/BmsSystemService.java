package com.cts.bms.bmssystem.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import com.cts.bms.bmssystem.model.Customer;
import com.cts.bms.bmssystem.model.Loan;
import com.cts.bms.bmssystem.model.UserLoginDetails;

@Component
public interface BmsSystemService {
	
	
		public void registerCustomer(String topic, Customer customer);
		
		public void updateCustomer(String topic, Customer customer);
		

		public void applyLoan(String topic, Loan loan);

		public void retrieveLoans(Integer accountNumber);
		
		public void saveMessage(String message);
		
		public String getMessage();

		public boolean login(UserLoginDetails userLoginDetails, HttpSession session, ModelMap warning);

		public void saveLoans(List<Loan> loanList);

		public List<Loan> returnLoans();

		public boolean validateToken(String token);

	}


