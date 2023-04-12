package com.atticket.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SeatsResponseDto {
	//좌석 조회시 사용하는 ResponseDTO입니다

	//공연 날짜
	private String showDate;

	//공연 시간
	private String showTime;

	//공연 회차
	private String session;

	//공연 등급별 잔여 좌석
	private List<RemainSeat> remainSeatList;

	@Builder
	public SeatsResponseDto(String showDate, String showTime, String session, List<RemainSeat> remainSeatList) {
		this.showDate = showDate;
		this.showTime = showTime;
		this.session = session;
		this.remainSeatList = remainSeatList;
	}

	//공연 등급별 잔여 좌석
	@Getter
	public static class RemainSeat {

		//좌석 등급
		private String type;

		//좌석 가격
		private int price;

		//좌석 수
		private int count;

		@Builder
		public RemainSeat(String type, int count, int price) {
			this.type = type;
			this.count = count;
			this.price = price;
		}

	}
}
