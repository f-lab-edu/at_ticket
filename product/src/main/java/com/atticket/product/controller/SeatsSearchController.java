package com.atticket.product.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atticket.product.dto.request.SeatsSearchReqDto;
import com.atticket.product.dto.response.SeatsSearchResDto;

//좌석 조회
@RestController
@RequestMapping("/seats")
public class SeatsSearchController {

	//좌석 조회
	@GetMapping("")
	public SeatsSearchResDto getSeats(SeatsSearchReqDto seatsRequest) {
		/** 공연의 날짜 시간별
		 *  좌석 등급과 잔여 좌석을 리턴해줍니다.
		 */

		//공연 날짜/회차별 좌석 정보
		return SeatsSearchResDto.builder()
			.showDate("20230407")
			.showTime("1800")
			.session("1회차")
			.remainSeatList(List.of(
					SeatsSearchResDto.RemainSeat.builder()
						.type("A")
						.count(10)
						.price(1000)
						.build(),
					SeatsSearchResDto.RemainSeat.builder()
						.type("B")
						.count(8)
						.price(5000)
						.build()
				)
			)
			.build();
	}
}
