package com.atticket.product.kafka.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.atticket.common.kafka.payload.WishProduct;
import com.atticket.common.kafka.topic.Topic;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductProducer {

	private final KafkaTemplate<String, WishProduct> kafkaTemplate;

	/**
	 * 관심 상품 등록 알림 메일 발송 이벤트를 발행한다
	 * @param data
	 */
	public void sendNotifyMail(WishProduct data) {

		log.info(String.format("Product[sendNotifyMail] 관심 상품 등록 send -> %s", data.toString()));

		Message<WishProduct> message = MessageBuilder
			.withPayload(data)
			.setHeader(KafkaHeaders.TOPIC, Topic.NOTIFY_MAIL.name())
			.build();
		kafkaTemplate.send(message);

	}

}
