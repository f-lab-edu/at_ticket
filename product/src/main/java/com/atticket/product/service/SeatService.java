package com.atticket.product.service;

import java.util.List;

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

	public List<Seat> getSeatsBySeatIds(List<Long> seatIds) {

		return seatRepository.findAllById(seatIds);
	}
}
