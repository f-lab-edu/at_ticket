package com.atticket.product.repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Repository;

import com.atticket.product.domain.Seat;

@Repository
public class SeatRepository {

	private List<Seat> seatTestDatas = new CopyOnWriteArrayList<>();

	public Optional<Seat> findById(Long seatId) {

		return seatTestDatas.stream()
			.filter(
				seat -> seat.getId().equals(seatId)
			).findAny();
	}

}
