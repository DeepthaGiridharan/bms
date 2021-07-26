package com.cts.bms.bmssystem.kafkaconfig;

import java.util.concurrent.CountDownLatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import com.cts.bms.bmssystem.service.BmsSystemService;

public class ApplyLoanConsumer {
	@Autowired
	BmsSystemService bmsSystemService;

	private CountDownLatch latch = new CountDownLatch(1);

	@KafkaListener(id = "${spring.kafka.applyLoanMessageConsumer.groupId}", topics = {
			"${spring.kafka.topic.applyLoanMessage}",
			"${spring.kafka.topic.failedLoanMessage}" }, containerFactory = "applyLoanMessageKafkaListenerContainerFactory")
	public void receive(String payload) {
		bmsSystemService.saveMessage(payload);
		latch.countDown();
	}

}
