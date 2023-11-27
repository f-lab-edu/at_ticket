package com.atticket.reservation.kafka.consumer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.atticket.common.kafka.payload.CheckReserResponse;
import com.atticket.common.kafka.payload.CheckReservation;
import com.atticket.common.kafka.topic.Topic;
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
	private final KafkaTemplate<String, CheckReservation> kafkaTemplate;

	/**
	 * 에약 가능 여부를 확인하고, 저장한다.
	 * @param data
	 */
	@KafkaListener(topics = "CHECK_RESERVATION", groupId = "myGroup", errorHandler = "kafkaErrorHandler")
	public void consumer(CheckReservation data) throws JsonMappingException, JsonProcessingException {

		Long showId = data.getShowId();
		List<Long> seatIds = data.getSeatIds();
		String userId = data.getUserId();

		log.info(String.format("Reservation[CHECK_RESERVATION] 좌석 예약 가능 여부 이벤트 received -> %s", data));

		if (reservedSeatService.existsReservedSeat(showId, seatIds)) {
			preReservationResponse(500, "실패", seatIds, showId, userId);
			throw new IllegalStateException("해당 좌석은 예약 할 수 없습니다.");
		}

		if (preReservedSeatRepository.existsByShowIdAndSeatIdIn(showId, seatIds)) {
			preReservationResponse(500, "실패", seatIds, showId, userId);
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

		preReservationResponse(200, "성공", seatIds, showId, userId);
	}

	public void preReservationResponse(int code, String message, List<Long> seatIds, Long showId, String userId) {

		CheckReserResponse checkReserResponse = CheckReserResponse.builder()
			.code(code)
			.message(message)
			.seatIds(seatIds)
			.showId(showId)
			.userId(userId)
			.build();

		Message<CheckReserResponse> response = MessageBuilder
			.withPayload(checkReserResponse)
			.setHeader(KafkaHeaders.TOPIC, Topic.CHECK_RESERVATION_RESULT.name())
			.build();

		kafkaTemplate.send(response);

	}

}
