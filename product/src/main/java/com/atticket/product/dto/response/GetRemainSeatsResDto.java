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

		private Long id;

		// 좌석 공간 (e.g. 1층, 2층, .../ A존, B존, ...)
		private String space;

		// 좌석 x 좌표
		private String locX;

		// 좌석 y 좌표
		private String locY;

		private String row;

		private int rowNum;

		private String grade;

		private int price;
	}
}
