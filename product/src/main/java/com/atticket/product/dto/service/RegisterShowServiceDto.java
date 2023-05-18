package com.atticket.product.dto.service;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class RegisterShowServiceDto {

	//날짜
	private final String date;

	//시간
	private final String time;

	//회차
	private final String session;

	//공연 홀 id
	private final String hallId;

	//좌석 정보 (좌석ID, 등급)
	private List<SeatInfo> seats;

	@Getter
	@Builder
	@ToString
	public static class SeatInfo {

		//좌석 등급
		private String grade;

		//좌석 Id
		private List<String> ids;

	}

}
