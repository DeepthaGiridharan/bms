package com.cts.bms.bmssystem.kafkaconfig;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import com.cts.bms.bmssystem.model.Loan;
import com.cts.bms.bmssystem.service.BmsSystemService;

public class RetrieveLoanConsumer {
	@Autowired
	BmsSystemService bmsSystemService;

	private CountDownLatch latch = new CountDownLatch(1);
	@KafkaListener(id = "${spring.kafka.retrieveLoanConsumer.groupId}", topics = { "${spring.kafka.topic.retrieveLoan}",
	"${spring.kafka.topic.failedRetrieveLoan}" }, containerFactory = "retrieveLoanKafkaListenerContainerFactory")
public void receive(List<Loan> payload) {
		System.out.println("portal consumer for loan retrieve:"+ payload.size());
		 bmsSystemService.saveLoans(payload);
latch.countDown();
}
}
