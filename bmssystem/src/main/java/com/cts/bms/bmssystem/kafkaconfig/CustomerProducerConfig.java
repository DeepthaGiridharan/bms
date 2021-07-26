package com.cts.bms.bmssystem.kafkaconfig;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.cts.bms.bmssystem.model.Customer;
import com.fasterxml.jackson.databind.ser.std.StringSerializer;

@Configuration
@EnableKafka
public class CustomerProducerConfig {
	@Value("${spring.kafka.bootstrapServers}")
	private String bootstrapServers;
	
	@Bean
	public Map<String, Object> customerProducerConfigs() {

		Map<String, Object> props = new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		return props;
	}
	@Bean
	public ProducerFactory<String, Customer> customerProducerFactory() {
		return new DefaultKafkaProducerFactory<>(customerProducerConfigs());
	}

	@Bean("customerProducerKafkaTemplate")
	public KafkaTemplate<String, Customer> customerProducerKafkaTemplate() {
		return new KafkaTemplate<>(customerProducerFactory());
	}

	@Bean
	public CustomerProducer customerProducer() {
		return new CustomerProducer();
	}
}
