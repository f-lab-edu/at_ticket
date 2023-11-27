package com.atticket.reservation.kafka.producer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.atticket.common.kafka.payload.CheckReserResponse;

import lombok.extern.slf4j.Slf4j;

// 컨슈머 리스너
@Component
@Slf4j
public class ResultListener {

	@KafkaListener(topics = "CHECK_RESERVATION_RESULT", groupId = "myGroup")
	public void listen(CheckReserResponse result) {

		// 여기서 result 객체를 받아 처리
		// 예를 들어, 받은 결과를 데이터베이스에 저장하거나 다른 서비스로 전달 등의 작업 수행

		log.info(String.format("Reservation[check_reservation_result] 저장 결과 received -> %s", result));
	}
}
