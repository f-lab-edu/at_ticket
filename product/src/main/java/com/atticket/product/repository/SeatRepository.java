package com.atticket.product.repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Repository;

import com.atticket.product.domain.Seat;

@Repository
public class SeatRepository {

	private List<Seat> seatTestDatas = new CopyOnWriteArrayList<>();

	public SeatRepository() {
		for (long i = 0; i < 30; i++) {
			seatTestDatas.add(
				Seat.builder()
					.id(i)
					.space("1층")
					.locX(String.valueOf(i * 10))
					.locY(String.valueOf(i * 10))
					.row("A")
					.rowNum((int)(i + 1))
					.hallId("1")
					.build()
			);
		}
	}

	public Optional<Seat> findById(Long seatId) {

		return seatTestDatas.stream()
			.filter(
				seat -> seat.getId().equals(seatId)
			).findAny();
	}

}
