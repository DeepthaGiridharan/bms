package com.cts.bms.applyloan.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.cts.bms.applyloan.ApplyloanApplication;
import com.cts.bms.applyloan.config.ApplyLoanProducer;
import com.cts.bms.applyloan.config.RetrieveLoanProducer;
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
	
	@Autowired
	private ApplyLoanProducer applyLoanProducer;

	@Autowired
	private RetrieveLoanProducer retrieveLoanProducer;

	
	@Value("${spring.kafka.topic.applyLoanMessage}")
	String APPLY_LOAN_MESSAGE_TOPIC;

	@Value("${spring.kafka.topic.failedLoanMessage}")
	String FAILED_LOAN_MESSAGE_TOPIC;

	@Value("${spring.kafka.topic.retrieveLoan}")
	String RETRIEVE_LOAN_TOPIC;

	@Value("${spring.kafka.topic.failedRetrieveLoan}")
	String FAILED_RETRIEVE_LOAN_TOPIC;


	@Override
	public List<Loan> getAllLoansByAccountId(Integer accountId) {
		try {
		LOGGER.info("Displaying loans of accountId:"+accountId);
		List<Loan> loanList=loanRepository.getAllLoansByAccountId(accountId);
		System.out.println(loanList.size());
		retrieveLoanProducer.send(RETRIEVE_LOAN_TOPIC, loanList);
		return loanList;
		}
		catch (Exception e) {
			
			applyLoanProducer.send(FAILED_RETRIEVE_LOAN_TOPIC, null);
			LOGGER.error("Exception");
			return null;
		}
		
	}

	@Override
	public void saveLoanDetails(Loan loanDetails) {
		
		Loan saveLoan=new Loan();
		String message;
		try {
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
		message= "Loan Applied Successfully";
		applyLoanProducer.send(APPLY_LOAN_MESSAGE_TOPIC, message);
		loanRepository.save(saveLoan);
		
		}
		catch (Exception e) {
			message = "Process Failed. Please try again.";
			applyLoanProducer.send(FAILED_LOAN_MESSAGE_TOPIC, message);
			LOGGER.error("Exception");
			
		}
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
