package com.atticket.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductDetailResponseDto {

	//공연 이름
	private String name;

	//공연 세부 정보
	private Contents contents;

	//공연 날짜/회차별 좌석 정보
	private SeatsResponseDto seatInfo;

	@Builder
	public ProductDetailResponseDto(String name, Contents contents, SeatsResponseDto seatInfo) {
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

}
