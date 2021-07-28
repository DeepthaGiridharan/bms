package com.cts.bms.register.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;

public class CustomerProducer {
	@Autowired
	@Qualifier("customerProducerKafkaTemplate")
	private KafkaTemplate<String, String> customerProducerKafkaTemplate;

	public void send(String topic, String payload) {
		customerProducerKafkaTemplate.send(topic, payload);
	}
}
