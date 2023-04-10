package com.atticket.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.atticket.dto.GetProductDetailResponse;

@RestController
public class ProductSearchController {

	//공연 상세 조회
	@GetMapping("/products/{productId}")
	public GetProductDetailResponse productDetail(@PathVariable String productId) {

		//공연 내용
		GetProductDetailResponse.Contents contents = GetProductDetailResponse.Contents.builder()
			.showExplain("공연설명")
			.showTime("1시간")
			.build();

		//공연 날짜/회차별 좌석 정보
		GetProductDetailResponse.SeatInfo seatInfo = GetProductDetailResponse.SeatInfo.builder()
			.showDate("20230407")
			.session("1회차")
			.remainSeatList(List.of(
				GetProductDetailResponse.RemainSeat.builder()
					.type("A")
					.count(10)
					.build()
				,
				GetProductDetailResponse.RemainSeat.builder()
					.type("B")
					.count(8)
					.build()

			))
			.build();

		//공연 상세
		return GetProductDetailResponse.builder()
			.name("캣츠")
			.contents(contents)
			.seatInfo(List.of(seatInfo))
			.build();

	}
}
