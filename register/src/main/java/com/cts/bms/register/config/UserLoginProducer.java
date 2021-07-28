package com.cts.bms.register.config;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;

import com.cts.bms.register.model.UserLoginDetails;

public class UserLoginProducer {
	@Autowired
	@Qualifier("userLoginProducerKafkaTemplate")
	private KafkaTemplate<String, UserLoginDetails> userLoginProducerKafkaTemplate;

	public void send(String topic, UserLoginDetails payload) {
		userLoginProducerKafkaTemplate.send(topic, payload);
	}

}
