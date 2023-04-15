package com.atticket.product.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductDetailResDto {
	//상품 이름
	private final String name;

	//상품 세부 정보
	private final Contents contents;

	//상품 날짜/회차별 좌석 정보
	private final SeatsSearchResDto seatInfo;

	//상품 세부 정보 클래스
	@Getter
	@Builder
	public static class Contents {

		//공연 설명
		private final String showExplain;

		//공연 시간
		private final String showTime;

	}
}
