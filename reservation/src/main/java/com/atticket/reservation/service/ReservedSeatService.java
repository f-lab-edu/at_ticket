package com.atticket.reservation.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.atticket.reservation.domain.ReservedSeat;
import com.atticket.reservation.repository.ReservedSeatRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservedSeatService {
	private final ReservedSeatRepository reservedSeatRepository;

	/**
	 * 공연의 예매된 좌석 리스트 조회
	 * @param showId
	 * @return
	 */
	public List<ReservedSeat> getReservedSeatsByShowId(Long showId) {
		return reservedSeatRepository.findShowSeatByShowId(showId);
	}

}