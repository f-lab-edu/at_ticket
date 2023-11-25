package com.atticket.reservation.kafka.consumer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.atticket.common.kafka.payload.CheckReservation;
import com.atticket.reservation.domain.PreReservedSeat;
import com.atticket.reservation.repository.PreReservedSeatRepository;
import com.atticket.reservation.service.ReservedSeatService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CheckReservationConsumer {

	private final ReservedSeatService reservedSeatService;
	private final PreReservedSeatRepository preReservedSeatRepository;

	/**
	 * 받은 데이터로 메일을 발송한다.
	 * @param data
	 */
	@KafkaListener(topics = "CHECK_RESERVATION", groupId = "myGroup", errorHandler = "kafkaErrorHandler")
	public void consumer(CheckReservation data) throws JsonMappingException, JsonProcessingException {

		Long showId = data.getShowId();
		List<Long> seatIds = data.getSeatIds();
		String userId = data.getUserId();

		log.info(String.format("Reservation[CHECK_RESERVATION] 좌석 예약 가능 여부 이벤트 received -> %s", data));

		if (reservedSeatService.existsReservedSeat(showId, seatIds)) {
			throw new IllegalStateException("해당 좌석은 예약 할 수 없습니다.");
		}

		if (preReservedSeatRepository.existsByShowIdAndSeatIdIn(showId, seatIds)) {
			throw new IllegalStateException("해당 좌석은 예약 할 수 없습니다.");
		}

		log.info("Reservation[CHECK_RESERVATION] 예약 진행");

		List<PreReservedSeat> preReservedSeats = seatIds.stream().map(seatId ->
			PreReservedSeat.builder()
				.showId(showId)
				.seatId(seatId)
				.userId(userId)
				.time(LocalDateTime.now())
				.build()
		).collect(Collectors.toList());
		preReservedSeatRepository.saveAll(preReservedSeats);

	}

}
