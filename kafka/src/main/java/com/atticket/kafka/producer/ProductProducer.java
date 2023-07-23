package com.atticket.kafka.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.atticket.kafka.payload.NotifyData;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductProducer {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductProducer.class);

	private final KafkaTemplate<String, NotifyData> kafkaTemplate;
	private static final String MAIL_TOPIC = "mail_notify";

	/**
	 * 상품에 관한 알림에 대한 이벤트를 발행한다.
	 * @param data
	 */
	public void sendMail(NotifyData data) {

		LOGGER.info(String.format("Message sent -> %s", data.toString()));

		Message<NotifyData> message = MessageBuilder
			.withPayload(data)
			.setHeader(KafkaHeaders.TOPIC, MAIL_TOPIC)
			.build();
		kafkaTemplate.send(message);

	}

}
