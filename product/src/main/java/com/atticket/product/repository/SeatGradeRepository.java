package com.atticket.product.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.atticket.product.domain.SeatGrade;

@Repository
public class SeatGradeRepository {

	private static List<SeatGrade> seatGradeTestDatas = new ArrayList<>(Arrays.asList(
		SeatGrade.builder()
			.id(1L)
			.showId(1L)
			.seatId(100L)
			.gradeId(1L)
			.productId(1L)
			.build(),
		SeatGrade.builder()
			.id(2L)
			.showId(1L)
			.seatId(101L)
			.gradeId(1L)
			.productId(1L)
			.build(),
		SeatGrade.builder()
			.id(3L)
			.showId(1L)
			.seatId(102L)
			.gradeId(2L)
			.productId(1L)
			.build()

	));

	public Optional<SeatGrade> findById(Long id) {

		return seatGradeTestDatas.stream()
			.filter(
				seat -> seat.getId().equals(id)
			).findAny();
	}

	public List<SeatGrade> findByshowId(Long showId) {

		return seatGradeTestDatas.stream()
			.filter(
				seatGrade -> seatGrade.getShowId().equals(showId)
			).collect(Collectors.toList());
	}

	public List<SeatGrade> findAll() {
		return seatGradeTestDatas;
	}
}
