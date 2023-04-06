package com.atticket.reservation.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductDetail {

	//공연 이름
	private String name;

	//공연 세부 정보
	private Contents contents;

	//공연 날짜/회차별 좌석 정보
	private List<SeatInfo> seatInfo;

	public ProductDetail() {

	}

	@Builder
	public ProductDetail(String name, Contents contents, List<SeatInfo> seatInfo) {
		this.name = name;
		this.contents = contents;
		this.seatInfo = seatInfo;
	}

	//공연 세부 정보 클래스
	@Getter
	public static class Contents {

		//공연 설명
		private String showExplain;

		//공연 시간
		private String showTime;

		@Builder
		public Contents(String showExplain, String showTime) {
			this.showExplain = showExplain;
			this.showTime = showTime;
		}

	}

	@Getter
	//공연 날짜/회차별 좌석 정보
	public static class SeatInfo {

		//공연 날짜
		private String showDate;

		//공연 회차
		private String session;

		//공연 등급별 잔여 좌석
		private List<RemainSeat> remainSeatList;

		@Builder
		public SeatInfo(String showDate, String session, List<RemainSeat> remainSeatList) {
			this.showDate = showDate;
			this.session = session;
			this.remainSeatList = remainSeatList;
		}
	}

	//공연 등급별 잔여 좌석
	@Getter
	public static class RemainSeat {

		//좌석 등급
		private String type;

		//좌석 수
		private int count;

		@Builder
		public RemainSeat(String type, int count) {
			this.type = type;
			this.count = count;
		}

	}

}
