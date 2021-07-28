package com.cts.bms.applyloan.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

public class ApplyLoanProducer {
	@Autowired
	private KafkaTemplate<String, String> applyLoanProducerKafkaTemplate;

	public void send(String topic, String payload) {
		applyLoanProducerKafkaTemplate.send(topic, payload);
	}
}
