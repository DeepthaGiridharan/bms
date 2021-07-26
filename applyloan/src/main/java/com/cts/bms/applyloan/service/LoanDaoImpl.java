package com.cts.bms.applyloan.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.cts.bms.applyloan.ApplyloanApplication;
import com.cts.bms.applyloan.model.Loan;
import com.cts.bms.applyloan.repository.LoanRepository;
import com.cts.bms.applyloan.restclient.LoginClient;


@Component
public class LoanDaoImpl implements LoanDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(ApplyloanApplication.class);


	@Autowired
	LoanRepository loanRepository;
	
	@Autowired
	LoginClient loginClient;
	
	
	@Override
	public List<Loan> getAllLoans() {
		LOGGER.info("Displaying all loans");
		return loanRepository.findAll();
	}

	@Override
	public List<Loan> getAllLoansByAccountId(Integer accountId) {
		LOGGER.info("Displaying loans of accountId:"+accountId);
		return loanRepository.getAllLoansByAccountId(accountId);
	}

	@Override
	public List<Loan> saveLoanDetails(Loan loanDetails) {
		Loan saveLoan=new Loan();
		if(loanDetails.getLoanType().equalsIgnoreCase("Personal")) {
			 saveLoan= Loan.builder()
					.accountId(loanDetails.getAccountId())
					.interestRate(loanDetails.getInterestRate())
					.duration(loanDetails.getDuration())
					.loanAmount(loanDetails.getLoanAmount())
					.loanDate(loanDetails.getLoanDate())
					.loanType(loanDetails.getLoanType())
					.annualIncome(loanDetails.getAnnualIncome())
					.currExp(loanDetails.getCurrExp())
					.designation(loanDetails.getDesignation())
					.companyName(loanDetails.getCompanyName())
					.totalExp(loanDetails.getTotalExp()).build();
			
					
		}
		else if(loanDetails.getLoanType().equalsIgnoreCase("Educational")) {
			 saveLoan= Loan.builder()
					.accountId(loanDetails.getAccountId())
					.interestRate(loanDetails.getInterestRate())
					.duration(loanDetails.getDuration())
					.loanAmount(loanDetails.getLoanAmount())
					.loanDate(loanDetails.getLoanDate())
					.loanType(loanDetails.getLoanType())
					.fatherAnnIncome(loanDetails.getFatherAnnIncome())
					.fatherCurrExp(loanDetails.getFatherCurrExp())
					.fatherTotalExp(loanDetails.getFatherTotalExp())
					.fatherName(loanDetails.getFatherName())
					.courseFee(loanDetails.getCourseFee())
					.courseName(loanDetails.getCourseName())
					.fatherOcc(loanDetails.getFatherOcc())
					.rationCardNo(loanDetails.getRationCardNo()).build();
					
			
		}
		LOGGER.info("Saving loan details");
		loanRepository.save(saveLoan);
		return loanRepository.getAllLoansByAccountId(loanDetails.getAccountId());
	}

	@Override
	public boolean validateToken(String token) {
		try {
			if (token == null) {
				LOGGER.info("Unauthorized");
				return false;
			}
			HttpStatus loginStatusCode = loginClient.validateToken(token).getStatusCode();
			if (loginStatusCode.equals(HttpStatus.OK)) {
				LOGGER.info("Success");
				return true;
			} else {
				LOGGER.info("Unauthorized");
				return false;
			}
		} catch (Exception e) {
			LOGGER.error("Error");
			return false;
		}
	}

	
	

}
