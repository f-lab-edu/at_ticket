package com.atticket.reservation.kafka.producer;

import java.time.LocalDateTime;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.atticket.common.kafka.payload.CheckReserResponse;
import com.atticket.reservation.domain.Reservation;
import com.atticket.reservation.repository.ReservationRepository;
import com.atticket.reservation.type.Status;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// 컨슈머 리스너
@Component
@Slf4j
@RequiredArgsConstructor
public class ResultListener {

	private final ReservationRepository reservationRepository;

	@KafkaListener(topics = "CHECK_RESERVATION_RESULT", groupId = "myGroup")
	public void listen(CheckReserResponse result) {

		// 여기서 result 객체를 받아 처리
		// 예를 들어, 받은 결과를 데이터베이스에 저장하거나 다른 서비스로 전달 등의 작업 수행

		log.info(String.format("Reservation[check_reservation_result] 저장 결과 received -> %s", result));

		if (result.getCode() == 200) {
			Reservation reservation = Reservation.builder()
				.showId(result.getShowId())
				.seatIds(result.getSeatIds())
				.userId(result.getUserId())
				.status(Status.WAIT_PAY)
				.time(LocalDateTime.now())
				.build();
			reservationRepository.save(reservation);
		}

	}
}
