package com.cts.bms.applyloan.service;

import java.util.List;

import com.cts.bms.applyloan.model.Loan;

public interface LoanDao {
	
	
	
	List<Loan> getAllLoansByAccountId(Integer accountId);
	
	void saveLoanDetails(Loan loanDetails);
	
	public boolean validateToken(String token);

}
