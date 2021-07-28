package com.cts.bms.bmslogin.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.kafka.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.cts.bms.bmslogin.mdel.UserLoginDetails;

import org.apache.kafka.common.serialization.StringDeserializer;

@Configuration
@EnableKafka
public class UserLoginConsumerConfig {
	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;

	@Bean
	public Map<String, Object> userLoginConsumerConfigs() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, "UserLoginConsumer");
		props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

		return props;
	}

	@Bean
	public ConsumerFactory<String, UserLoginDetails> userLoginConsumerFactory() {
		DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();

		Map<String, Class<?>> classMap = new HashMap<>();
		classMap.put("com.cts.bms.bmssystem.model.Customer", UserLoginDetails.class);
		typeMapper.setIdClassMapping(classMap);

		typeMapper.addTrustedPackages("*");

		JsonDeserializer<UserLoginDetails> deserializer = new JsonDeserializer<>(UserLoginDetails.class);
		deserializer.setTypeMapper(typeMapper);
		deserializer.setUseTypeMapperForKey(true);

		return new DefaultKafkaConsumerFactory<>(userLoginConsumerConfigs(), new StringDeserializer(),
				deserializer);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, UserLoginDetails> userLoginConsumerKafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, UserLoginDetails> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(userLoginConsumerFactory());

		return factory;
	}

	@Bean
	public UserLoginConsumer userLoginConsumer() {
		return new UserLoginConsumer();
	}

}
