package com.atticket.kafka.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.atticket.kafka.config.MailSender;
import com.atticket.kafka.payload.NotifyData;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotifyConsumer {
	private static final Logger LOGGER = LoggerFactory.getLogger(NotifyConsumer.class);

	private static final String MAIL_TOPIC = "mail_notify";

	private final MailSender mailSender;

	/**
	 * 받을 데이터로 메일을 발송한다.
	 * @param notifyData
	 */
	@KafkaListener(topics = MAIL_TOPIC, groupId = "myGroup")
	public void consume(NotifyData notifyData) throws Exception {
		LOGGER.info(String.format("Json Message received -> %s", notifyData.toString()));

		//메일 발송
		mailSender.Send(notifyData.getTitle(), notifyData.getMessage());

	}

}
