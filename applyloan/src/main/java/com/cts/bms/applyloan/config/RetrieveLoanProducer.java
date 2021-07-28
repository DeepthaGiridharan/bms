package com.cts.bms.applyloan.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

import com.cts.bms.applyloan.model.Loan;

public class RetrieveLoanProducer {
	@Autowired
	private KafkaTemplate<String, List<Loan>> retrieveLoanProducerKafkaTemplate;

	public void send(String topic, List<Loan> payload) {
		System.out.println("applyloan producer for loan retrieve:"+ payload.size());
		retrieveLoanProducerKafkaTemplate.send(topic, payload);
	}

}
