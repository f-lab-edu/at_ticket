package com.atticket.show.dto.request;

import java.util.List;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class RegisterShowReqDto {

	//상품 Id
	private String productId;

	//공연 시간/좌석 정보 리스트
	private List<ShowInfo> showList;

	//공연 시간/좌석 정보
	@Getter
	@ToString
	public static class ShowInfo {

		//날짜
		private String showDate;

		//시간
		private String showTime;

		//공연 홀 id
		private String showHallId;

		//좌석 정보 (좌석 ID, 등급)
		private List<SeatInfo> seatList;

	}

	@Getter
	@ToString
	public static class SeatInfo {

		//좌석 Id
		private String seatId;

		//좌석 등급
		private String seatGrade;

	}
}
