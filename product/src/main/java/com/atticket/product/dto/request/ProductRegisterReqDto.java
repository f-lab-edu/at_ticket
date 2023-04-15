package com.atticket.product.dto.request;

import java.util.List;

import lombok.Getter;

@Getter
public class ProductRegisterReqDto {

	//공연 ID
	private String showId;

	//공연 시간/좌석 정보
	private ShowInfo showInfo;

	//공연 시간/좌석 정보
	@Getter
	public static class ShowInfo {

		//날짜
		private String showDate;

		//시간
		private String showTime;

		//공연 홀 id
		private String hallId;

		//좌석 정보 (등급, 수)
		private List<SeatInfo> seatInfo;

	}

	@Getter
	public static class SeatInfo {

		//좌석 등급
		private String type;

		//좌석 수
		private int count;

	}
}
