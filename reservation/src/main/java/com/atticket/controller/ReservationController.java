package com.atticket.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atticket.common.BaseResponse;
import com.atticket.common.SampleDto;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
	/**
	 * 예약조회
	 * */
	@GetMapping("")
	public BaseResponse<SampleDto> getReservations() {
		return new BaseResponse<>(SampleDto.builder().content("hi").build());
	}

	/**
	 * 예약하기
	 */
	@PostMapping("")
	public BaseResponse<SampleDto> postReservation() {
		return new BaseResponse<>(SampleDto.builder().content("hi").build());
	}
}
