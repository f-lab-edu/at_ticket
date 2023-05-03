package com.atticket.product.repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.atticket.product.domain.ShowSeat;

@Repository
public class ShowSeatRepository {
	private List<ShowSeat> showSeatTestDatas = new CopyOnWriteArrayList<>(Arrays.asList(
		ShowSeat.builder()
			.id(1L)
			.showId(1L)
			.seatList("1,2,3,4,5,6,7")
			.gradeId(1L)
			.productId(1L)
			.build(),
		ShowSeat.builder()
			.id(2L)
			.showId(1L)
			.seatList("8,9,10,11")
			.gradeId(2L)
			.productId(1L)
			.build(),
		ShowSeat.builder()
			.id(3L)
			.showId(2L)
			.seatList("1,2,3,4,5,6,7,8")
			.gradeId(1L)
			.productId(1L)
			.build(),
		ShowSeat.builder()
			.id(4L)
			.showId(2L)
			.seatList("9,10,11")
			.gradeId(2L)
			.productId(1L)
			.build()
	));

	public Optional<ShowSeat> findById(String id) {
		return showSeatTestDatas.stream()
			.filter(
				showSeat -> showSeat.getId().equals(id)
			).findAny();
	}

	public List<ShowSeat> findShowSeatByProductId(Long productId) {

		return showSeatTestDatas.stream()
			.filter(
				showSeat -> showSeat.getProductId().equals(productId)
			).collect(Collectors.toList());
	}

	public List<ShowSeat> findShowSeatByShowId(Long showId) {

		return showSeatTestDatas.stream()
			.filter(
				showSeat -> showSeat.getShowId().equals(showId)
			).collect(Collectors.toList());
	}

	public Long save(ShowSeat showSeat) {
		showSeat.setId((long)showSeatTestDatas.size() + 1);
		showSeatTestDatas.add(showSeat);
		return (long)showSeatTestDatas.size() + 1;
	}

}
