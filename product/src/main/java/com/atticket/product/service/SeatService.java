package com.atticket.product.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.atticket.product.domain.Seat;
import com.atticket.product.repository.SeatRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SeatService {

	private final SeatRepository seatRepository;

	public Seat getSeatById(Long id) {
		return seatRepository.findById(id).orElse(null);
	}
	
	public Seat getSeatBySeatNoAndHallId(Long seatNo, Long hallId) {
		return seatRepository.findBySeatNoAndHallId(seatNo, hallId).orElse(null);
	}

	public List<Seat> getSeatsBySeatNoList(List<Long> seatIds, Long hallId) {

		List<Seat> seats = seatIds.stream()
			.map(s -> seatRepository.findBySeatNoAndHallId(s, hallId).orElse(null))
			.collect(Collectors.toList());

		return seats;
	}
}
