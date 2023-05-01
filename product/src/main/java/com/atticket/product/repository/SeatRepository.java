package com.atticket.product.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.atticket.product.domain.Seat;

@Repository
public class SeatRepository {

	private List<Seat> seatTestDatas = Collections.synchronizedList(new ArrayList<>());

	public Optional<Seat> findById(Long seatId) {

		return seatTestDatas.stream()
			.filter(
				seat -> seat.getId().equals(seatId)
			).findAny();
	}

	public List<Seat> findAll() {
		return seatTestDatas;
	}
}
