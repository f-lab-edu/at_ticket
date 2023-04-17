package com.atticket.product.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetProductDetailResDto {
	//상품 이름
	private final String productName;

	//상품 세부 정보
	private final ProductContents productContents;

	//장소 정보
	private final ProductPlace productPlace;

	//좌석 Type List
	private final List<SeatType> seatTypesList;

	//List<공연 상연일>
	private final List<String> showDateList;

	//상품 세부 정보 클래스
	@Getter
	@Builder
	public static class ProductContents {

		//상품 설명
		private final String productExplain;

		//러닝 타임
		private final String productRunningTime;

		//상연 시작 일자
		private final String productPeriodStr;

		//상연 종료 일자
		private final String productPeriodEnd;

	}

	//장소
	@Getter
	@Builder
	public static class ProductPlace {

		//주소
		private final String placeAddress;

		//전화 번호
		private final String placeNumber;

	}

	//좌석 등급
	@Getter
	@Builder
	public static class SeatType {

		//좌석 등급
		private final String seatGrade;

		//좌석 가격
		private final String seatPrice;

	}

}
