package com.atticket.reservation.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.atticket.common.response.BaseException;
import com.atticket.common.response.BaseStatus;
import com.atticket.reservation.domain.PreReservedSeat;
import com.atticket.reservation.repository.PreReservedSeatRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PreReservedSeatService {

	private final ReservedSeatService reservedSeatService;
	private final PreReservedSeatRepository preReservedSeatRepository;

	/**
	 * 선예약좌석 등록하기
	 */
	public void registerPreReservedSeat(Long showId, List<Long> seatIds, String userId) {

		if (reservedSeatService.existsReservedSeat(showId, seatIds)
			|| preReservedSeatRepository.existsByShowIdAndSeatIdIn(showId, seatIds)) {
			throw new BaseException(BaseStatus.EXIST_RESERVED_SEAT);
		}

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

	/**
	 * 선예약좌석 삭제하기
	 */
	public void deletePreReservedSeat(Long showId, List<Long> seatIds) {
		List<PreReservedSeat> preReservedSeats = preReservedSeatRepository.findByShowIdAndSeatIdIn(showId, seatIds);
		List<Long> ids = preReservedSeats.stream().map(d -> d.getId()).collect(Collectors.toList());
		preReservedSeatRepository.deleteAllById(ids);
	}
}
