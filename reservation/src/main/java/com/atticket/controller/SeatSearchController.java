package com.atticket.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atticket.dto.request.SeatsSearchRequestDto;
import com.atticket.dto.response.SeatsSearchResponseDto;

//좌석 조회
@RestController
@RequestMapping("/seats")
public class SeatSearchController {

	//좌석 조회
	@GetMapping("")
	public SeatsSearchResponseDto getSeats(SeatsSearchRequestDto seatsRequest) {
		/** 공연의 날짜 시간별
		 *  좌석 등급과 잔여 좌석을 리턴해줍니다.
		 */

		//공연 날짜/회차별 좌석 정보
		return SeatsSearchResponseDto.builder()
			.showDate("20230407")
			.showTime("1800")
			.session("1회차")
			.remainSeatList(List.of(
					SeatsSearchResponseDto.RemainSeat.builder()
						.type("A")
						.count(10)
						.price(1000)
						.build(),
					SeatsSearchResponseDto.RemainSeat.builder()
						.type("B")
						.count(8)
						.price(5000)
						.build()
				)
			)
			.build();
	}
}
