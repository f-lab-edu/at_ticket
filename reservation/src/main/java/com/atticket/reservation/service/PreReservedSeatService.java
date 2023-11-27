package com.atticket.reservation.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.atticket.common.kafka.payload.CheckReservation;
import com.atticket.reservation.domain.PreReservedSeat;
import com.atticket.reservation.kafka.producer.ReservationProducer;
import com.atticket.reservation.repository.PreReservedSeatRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PreReservedSeatService {

	private final ReservedSeatService reservedSeatService;
	private final PreReservedSeatRepository preReservedSeatRepository;
	private final ReservationProducer reservationProducer;

	/**
	 * 선예약좌석 등록하기
	 */
	public void registerPreReservedSeat(Long showId, List<Long> seatIds, String userId) throws
		ExecutionException,
		InterruptedException {

		//예약 가능 여부 이벤트 발행
		checkReservation(showId, seatIds, userId);

	}

	/**
	 * 선예약좌석 삭제하기
	 */
	public void deletePreReservedSeat(Long showId, List<Long> seatIds) {
		List<PreReservedSeat> preReservedSeats = preReservedSeatRepository.findByShowIdAndSeatIdIn(showId, seatIds);
		preReservedSeatRepository.deleteAll(preReservedSeats);
	}

	//예약 가능 여부 이벤트 발행
	public void checkReservation(Long showId, List<Long> seatIds, String userId) throws
		ExecutionException,
		InterruptedException {

		reservationProducer.checkReservation(CheckReservation.builder()
			.seatIds(seatIds)
			.showId(showId)
			.userId(userId)
			.build());
	}

}
