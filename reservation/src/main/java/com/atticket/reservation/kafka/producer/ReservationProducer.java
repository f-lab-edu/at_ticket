package com.atticket.reservation.kafka.producer;

import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.atticket.common.kafka.payload.CheckReservation;
import com.atticket.common.kafka.topic.Topic;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReservationProducer {

	private final KafkaTemplate<String, CheckReservation> kafkaTemplate;

	/**
	 * 예약 가능 여부 확인 이벤트를 발행한다.
	 */
	public void checkReservation(CheckReservation data) throws ExecutionException, InterruptedException {

		log.info(String.format("Reservation[checkReservation] 예약 가능 여부 확인 send -> %s", data.toString()));

		Message<CheckReservation> message = MessageBuilder
			.withPayload(data)
			.setHeader(KafkaHeaders.TOPIC, Topic.CHECK_RESERVATION.name())
			.build();

		//전송 결과 확인
		RecordMetadata metadata = kafkaTemplate.send(message).get().getRecordMetadata();
		log.info(metadata.toString());

	}
}

