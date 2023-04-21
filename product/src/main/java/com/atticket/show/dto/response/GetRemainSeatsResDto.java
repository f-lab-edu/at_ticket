package com.atticket.show.dto.response;

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

		private final String id;

		// 좌석 공간 (e.g. 1층, 2층, .../ A존, B존, ...)
		private final String space;

		// 좌석 x 좌표
		private final String locX;

		// 좌석 y 좌표
		private final String locY;

		private final String row;

		private final int rowNum;

		private final String grade;

		private final int price;
	}
}
