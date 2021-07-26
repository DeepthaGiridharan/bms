package com.cts.bms.bmssystem.kafkaconfig;

import java.util.concurrent.CountDownLatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import com.cts.bms.bmssystem.service.BmsSystemService;

public class CustomerConsumer {

	@Autowired
	BmsSystemService bmsSystemService;

	private CountDownLatch latch = new CountDownLatch(1);

	@KafkaListener(id = "${spring.kafka.customerMessageConsumer.groupId}", topics = {
			"${spring.kafka.topic.registerCustomerMessage}", "${spring.kafka.topic.updateCustomerMessage}",
			"${spring.kafka.topic.failedCustomerMessage}" }, containerFactory = "customerMessageConsumerKafkaListenerContainerFactory")
	public void receive(String payload) {
		bmsSystemService.saveMessage(payload);
		latch.countDown();
	}
}
