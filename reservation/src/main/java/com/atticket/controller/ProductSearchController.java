package com.atticket.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atticket.dto.response.ProductDetailResponseDto;
import com.atticket.dto.response.SeatsResponseDto;

//공연 조회
@RestController
@RequestMapping("/products")
public class ProductSearchController {

	//공연 상세 조회
	@GetMapping("/{productId}")
	public ProductDetailResponseDto getProductDetail(@PathVariable String productId) {
		/**
		 * 공연 Id를 입력받아
		 * 공연의 상세 내용을 리턴해줍니다
		 * */

		//공연 내용
		ProductDetailResponseDto.Contents contents = ProductDetailResponseDto.Contents.builder()
			.showExplain("공연설명")
			.showTime("1시간")
			.build();

		//공연 날짜/회차별 좌석 정보
		SeatsResponseDto seatInfo = SeatsResponseDto.builder()
			.showDate("20230407")
			.showTime("1800")
			.session("1회차")
			.remainSeatList(List.of(
					SeatsResponseDto.RemainSeat.builder()
						.type("A")
						.count(10)
						.price(1000)
						.build(),
					SeatsResponseDto.RemainSeat.builder()
						.type("B")
						.count(8)
						.price(5000)
						.build()
				)
			)
			.build();

		//공연 상세
		return ProductDetailResponseDto.builder()
			.name("캣츠")
			.contents(contents)
			.seatInfo(seatInfo)
			.build();

	}
}
