package com.cts.bms.bmssystem.kafkaconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;

import com.cts.bms.bmssystem.model.Loan;

public class ApplyLoanProducer {
	@Autowired
	@Qualifier("applyLoanProducerKafkaTemplate")
	private KafkaTemplate<String, Loan> applyLoanProducerKafkaTemplate;

	public void send(String topic, Loan payload) {
		applyLoanProducerKafkaTemplate.send(topic, payload);
	}
}
