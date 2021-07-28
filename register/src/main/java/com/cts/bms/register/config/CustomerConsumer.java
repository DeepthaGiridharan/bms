package com.cts.bms.register.config;

import java.util.concurrent.CountDownLatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;

import com.cts.bms.register.model.Customer;
import com.cts.bms.register.service.CustomerDao;

import org.springframework.messaging.handler.annotation.Header;

public class CustomerConsumer {
	@Autowired
	CustomerDao customerService;

	private CountDownLatch latch = new CountDownLatch(1);

	@KafkaListener(id = "${spring.kafka.customerConsumer.groupId}", topics = { "${spring.kafka.topic.registerCustomer}",
			"${spring.kafka.topic.updateCustomer}" }, containerFactory = "customerConsumerKafkaListenerContainerFactory")
	public void receive(@Header(KafkaHeaders.RECEIVED_TOPIC) String topic, Customer payload) {
		if(topic.equalsIgnoreCase("REGISTER_CUSTOMER_TOPIC")) {
			customerService.registerCustomer(payload);
			latch.countDown();
		}
		else if(topic.equalsIgnoreCase("UPDATE_CUSTOMER_TOPIC"))
		customerService.updateCustomer(payload);
		latch.countDown();
	}
}
