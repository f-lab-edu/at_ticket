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
			.seatList(convertListToString(testSeatListData1))
			.gradeId(1L)
			.productId(1L)
			.build(),
		ShowSeat.builder()
			.id(2L)
			.showId(1L)
			.seatList(convertListToString(testSeatListData2))
			.gradeId(2L)
			.productId(1L)
			.build(),
		ShowSeat.builder()
			.id(3L)
			.showId(2L)
			.seatList(convertListToString(testSeatListData3))
			.gradeId(1L)
			.productId(1L)
			.build(),
		ShowSeat.builder()
			.id(4L)
			.showId(2L)
			.seatList(convertListToString(testSeatListData4))
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

	/**
	 * seatList을 String -> List<Long> 반환
	 * @param stringSeatList
	 * @return
	 */
	public List<Long> convertStringToList(String stringSeatList) {

		List<Long> seatList = new ArrayList<>();
		if (!stringSeatList.isBlank()) {
			String[] seatString = (stringSeatList).split(",");
			for (String seat : seatString) {
				seatList.add(Long.parseLong(seat));
			}
		}

		return seatList;
	}

	/**
	 * seatList을 List<Long> -> String 반환
	 * @param seatList
	 * @return
	 */
	public String convertListToString(List<Long> seatList) {

		List<String> seatStringList = seatList.stream().map(s -> Long.toString(s)).collect(Collectors.toList());
		return String.join(",", seatStringList);
	}

}
