package com.atticket.kafka.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.atticket.kafka.payload.Product;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductKafkaProducer {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductKafkaProducer.class);

	private final KafkaTemplate<String, Product> kafkaTemplate;
	private static final String MY_TOPIC = "product";

	public void sendMessage(Product data) {

		LOGGER.info(String.format("Message sent -> %s", data.toString()));

		Message<Product> message = MessageBuilder
			.withPayload(data)
			.setHeader(KafkaHeaders.TOPIC, MY_TOPIC)
			.build();
		kafkaTemplate.send(message);

	}

}
