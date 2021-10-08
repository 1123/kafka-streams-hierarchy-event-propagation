package com.example.demo.producer;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.HashMap;

import static org.apache.kafka.common.config.SaslConfigs.SASL_JAAS_CONFIG;
import static org.apache.kafka.common.config.SaslConfigs.SASL_MECHANISM;

@SpringBootApplication
@EnableKafka
@EnableScheduling
public class ProducerApp {

	public static void main(String[] args) {
		SpringApplication.run(ProducerApp.class, args);
	}

	@Bean
	public KafkaTemplate<String, Tree<Integer>> containerTemplate(
			ProducerFactory<String, Tree<Integer>> containerProducerFactory) {
		return new KafkaTemplate<>(containerProducerFactory);
	}

	@Bean
	public ProducerFactory<String, Tree<Integer>> containerProducerFactory(
			@Value("${spring.kafka.properties.bootstrap.servers}") String bootstrapServers,
			@Value("${spring.kafka.properties.security.protocol}") String securityProtocol,
			@Value("${spring.kafka.properties.sasl.mechanism}") String saslMechanism,
			@Value("${spring.kafka.properties.sasl.jaas.config}") String saslJaasConfig

	) {
		HashMap<String, Object> configProps = new HashMap<>();
		configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		configProps.put(SASL_JAAS_CONFIG, saslJaasConfig);
		configProps.put(SASL_MECHANISM, saslMechanism);
		configProps.put("security.protocol", securityProtocol);
		configProps.put(
				ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
				StringSerializer.class);
		configProps.put(
				ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
				TreeSerializer.class);
		configProps.put("acks", "-1");
		return new DefaultKafkaProducerFactory<>(configProps);
	}

	@Bean
	public ProducerFactory<String, String> eventProducerFactory(
			@Value("${spring.kafka.properties.bootstrap.servers}") String bootstrapServers,
			@Value("${spring.kafka.properties.security.protocol}") String securityProtocol,
			@Value("${spring.kafka.properties.sasl.mechanism}") String saslMechanism,
			@Value("${spring.kafka.properties.sasl.jaas.config}") String saslJaasConfig

	) {
		HashMap<String, Object> configProps = new HashMap<>();
		configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		configProps.put(SASL_JAAS_CONFIG, saslJaasConfig);
		configProps.put(SASL_MECHANISM, saslMechanism);
		configProps.put("security.protocol", securityProtocol);
		configProps.put(
				ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
				StringSerializer.class);
		configProps.put(
				ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
				StringSerializer.class);
		configProps.put("acks", "-1");
		return new DefaultKafkaProducerFactory<>(configProps);
	}

	@Bean
	public KafkaTemplate<String, String> eventTemplate(
			ProducerFactory<String, String> eventProducerFactory) {
		return new KafkaTemplate<>(eventProducerFactory);
	}

}

