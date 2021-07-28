package com.cts.bms.applyloan.config;

import java.util.concurrent.CountDownLatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import com.cts.bms.applyloan.model.Loan;
import com.cts.bms.applyloan.service.LoanDao;

public class ApplyLoanConsumer {
	@Autowired
	LoanDao loanService;

	private CountDownLatch latch = new CountDownLatch(1);

	@KafkaListener(id = "${spring.kafka.applyLoanConsumer.groupId}", topics = "${spring.kafka.topic.applyLoan}", containerFactory = "applyLoanConsumerKafkaListenerContainerFactory")
	public void receive(Loan payload) {
		System.out.println("applyloan consumer ");
		loanService.saveLoanDetails(payload);
		latch.countDown();
	}
}
