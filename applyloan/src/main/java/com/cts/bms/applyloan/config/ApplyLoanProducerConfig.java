package com.cts.bms.applyloan.config;

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
import org.apache.kafka.common.serialization.StringSerializer;

@Configuration
@EnableKafka
public class ApplyLoanProducerConfig {
	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;

	@Bean
	public Map<String, Object> applyLoanProducerConfigs() {
		Map<String, Object> props = new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

		return props;
	}

	@Bean
	public ProducerFactory<String, String> applyLoanProducerFactory() {
		return new DefaultKafkaProducerFactory<>(applyLoanProducerConfigs());
	}

	@Bean
	public KafkaTemplate<String, String> applyLoanProducerKafkaTemplate() {
		return new KafkaTemplate<>(applyLoanProducerFactory());
	}

	@Bean
	public ApplyLoanProducer applyLoanMessageProducer() {
		return new ApplyLoanProducer();
	}
}
