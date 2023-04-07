package com.atticket.reservation.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atticket.reservation.dto.ProductDetailSearch;
import com.atticket.reservation.dto.ProductDetail;

@Controller
public class ProductSearchController {

	//공연 상세 조회
	@GetMapping("/products/detailSearch")
	@ResponseBody
	public ProductDetail productDetail(@ModelAttribute ProductDetailSearch productDetailSearch) {

		//공연 내용
		ProductDetail.Contents contents = ProductDetail.Contents.builder()
			.showExplain("공연설명")
			.showTime("1시간")
			.build();

		//공연 날짜/회차별 좌석 정보
		ProductDetail.SeatInfo seatInfo = ProductDetail.SeatInfo.builder()
			.showDate("20230407")
			.session("1회차")
			.remainSeatList(List.of(
				ProductDetail.RemainSeat.builder()
					.type("A")
					.count(10)
					.build()
				,
				ProductDetail.RemainSeat.builder()
					.type("B")
					.count(8)
					.build()

			))
			.build();

		//공연 상세
		return ProductDetail.builder()
				.name("캣츠")
				.contents(contents)
				.seatInfo(List.of(seatInfo))
				.build();

	}
}
