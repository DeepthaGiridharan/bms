package com.cts.bms.bmssystem.kafkaconfig;


import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
@EnableKafka

public class CustomerConsumerConfig {
	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;

	@Bean
	public Map<String, Object> customerConsumerConfig() {

		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, "CustomerConsumer");
		props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

		return props;
	}

	@Bean
	public ConsumerFactory<String, String[]> customerConsumerFactory() {
		JsonDeserializer<String[]> deserializer = new JsonDeserializer<String[]>();
		deserializer.addTrustedPackages("*");
		return new DefaultKafkaConsumerFactory<>(customerConsumerConfig(), new StringDeserializer(),
				deserializer);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, String[]> customerConsumerKafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, String[]> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(customerConsumerFactory());

		return factory;
	}

	@Bean
	public CustomerConsumer customerConsumer() {
		return new CustomerConsumer();
	}
}



}
