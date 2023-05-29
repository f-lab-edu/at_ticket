package com.atticket.product.dto.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegisterShowServiceDto {

	//공연 시간/좌석 정보 리스트
	private List<ShowInfo> showInfos;

	//공연 시간/좌석 정보
	@Getter
	@Builder
	public static class ShowInfo {

		//날짜
		private LocalDate date;

		//시간
		private LocalTime time;

		//회차
		private int session;

		//공연 홀 id
		private Long hallId;

		//좌석 등급 매핑 정보 (좌석ID, 등급)
		private List<SeatInfo> seatInfos;

	}

	@Getter
	@Builder
	public static class SeatInfo {

		//좌석 등급
		private Long gradeId;

		//좌석 Id
		private List<Long> seatIds;

	}

}
