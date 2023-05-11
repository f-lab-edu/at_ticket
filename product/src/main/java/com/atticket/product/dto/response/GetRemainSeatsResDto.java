package com.atticket.product.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetRemainSeatsResDto {

	private final List<ShowSeat> showSeats;

	@Getter
	@Builder
	public static class ShowSeat {

		private final Long id;
		// 좌석 공간 (e.g. 1층, 2층, .../ A존, B존, ...)
		private final String space;
		// 좌석 x 좌표
		private final String locX;
		// 좌석 y 좌표
		private final String locY;
		// 행
		private final String row;
		// 행 번호
		private final int rowNum;
		//등급
		private final String grade;
		//가격
		private final int price;
	}
}
