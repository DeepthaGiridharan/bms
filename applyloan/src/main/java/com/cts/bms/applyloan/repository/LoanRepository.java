package com.cts.bms.applyloan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.bms.applyloan.model.Loan;


@Repository
public interface LoanRepository extends JpaRepository<Loan,Integer> {
	
	List<Loan> getAllLoansByAccountId(Integer accountId);
	
	

}
