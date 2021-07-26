package com.cts.bms.bmslogin.config;

import java.util.concurrent.CountDownLatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import com.cts.bms.bmslogin.mdel.UserLoginDetails;
import com.cts.bms.bmslogin.service.LoginService;

public class UserLoginConsumer {
	
	@Autowired
	LoginService loginService;

	private CountDownLatch latch = new CountDownLatch(1);

	@KafkaListener(id = "${spring.kafka.userLoginConsumer.groupId}", topics = "${spring.kafka.topic.userLogin}", containerFactory = "userLoginConsumerKafkaListenerContainerFactory")
	public void receive(@Header(KafkaHeaders.RECEIVED_TOPIC) String topic, UserLoginDetails payload) {
		loginService.saveCredentials(payload);
		latch.countDown();
	}

}
