package com.atticket.kafka.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.atticket.kafka.payload.Product;

@Service
public class ProductKafkaConsumer {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductKafkaConsumer.class);

	private static final String MY_TOPIC = "product";

	@KafkaListener(topics = MY_TOPIC, groupId = "myGroup")
	public void consume(Product product) {
		LOGGER.info(String.format("Json Message received -> %s", product.toString()));

		//메일 전송 기능 추가 예정
	}

}
