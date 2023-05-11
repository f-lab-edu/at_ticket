package com.atticket.product.dto.request;

import java.util.List;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class RegisterShowReqDto {

	//상품 Id
	private Long productId;
	//공연 시간/좌석 정보 리스트
	private List<ShowInfo> shows;

	//공연 시간/좌석 정보
	@Getter
	@ToString
	public static class ShowInfo {

		//날짜
		private String date;
		//시간
		private String time;
		//공연 홀 id
		private String hallId;
		//좌석 정보 (좌석ID, 등급)
		private List<SeatInfo> seats;

	}

	@Getter
	@ToString
	public static class SeatInfo {

		//좌석 Id
		private String id;
		//좌석 등급
		private String grade;
	}
}
