package com.atticket.product.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SeatsSearchResDto {
	//좌석 조회시 사용하는 ResponseDTO입니다

	//공연 날짜
	private final String showDate;

	//공연 시간
	private final String showTime;

	//공연 회차
	private final String session;

	//공연 등급별 잔여 좌석
	private final List<RemainSeat> remainSeatList;

	//공연 등급별 잔여 좌석
	@Getter
	@Builder
	public static class RemainSeat {

		//좌석 등급
		private final String type;

		//좌석 가격
		private final int price;

		//좌석 수
		private final int count;

	}
}
