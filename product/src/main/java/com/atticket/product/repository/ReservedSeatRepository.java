package com.atticket.product.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.atticket.product.domain.ReservedSeat;

@Repository
public class ReservedSeatRepository {

	private List<ReservedSeat> reservedSeatTestDatas = Collections.synchronizedList(new ArrayList<>(Arrays.asList(
			ReservedSeat.builder()
				.id(1L)
				.showId(1L)
				.seatId(1L)
				.build(),
			ReservedSeat.builder()
				.id(2L)
				.showId(1L)
				.seatId(2L)
				.build(),
			ReservedSeat.builder()
				.id(3L)
				.showId(1L)
				.seatId(8L)
				.build(),
			ReservedSeat.builder()
				.id(4L)
				.showId(2L)
				.seatId(1L)
				.build()

		))
	);

	public Optional<ReservedSeat> findById(String id) {
		return reservedSeatTestDatas.stream()
			.filter(
				reservedSeat -> reservedSeat.getId().equals(id)
			).findAny();
	}

	public Optional<ReservedSeat> findReservedSeatBySeatId(Long seatId) {

		return reservedSeatTestDatas.stream()
			.filter(
				reservedSeat -> reservedSeat.getSeatId().equals(seatId)
			).findAny();
	}

	public List<ReservedSeat> findShowSeatByShowId(Long showId) {

		return reservedSeatTestDatas.stream()
			.filter(
				reservedSeat -> reservedSeat.getShowId().equals(showId)
			).collect(Collectors.toList());
	}

}
