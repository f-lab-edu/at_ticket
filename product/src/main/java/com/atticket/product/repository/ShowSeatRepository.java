package com.atticket.product.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.atticket.product.domain.ShowSeat;

@Repository
public class ShowSeatRepository {

	private List<Long> testSeatListData1 = Arrays.asList(1L, 2L, 3L, 4L, 5L, 6L, 7L);
	private List<Long> testSeatListData2 = Arrays.asList(8L, 9L, 10L, 11L);
	private List<Long> testSeatListData3 = Arrays.asList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L);
	private List<Long> testSeatListData4 = Arrays.asList(9L, 10L, 11L);

	private List<ShowSeat> showSeatTestDatas = new ArrayList<>(Arrays.asList(
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

}
