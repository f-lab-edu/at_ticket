package com.atticket.reservation.controller;

import static com.atticket.common.response.BaseResponse.ok;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atticket.common.response.BaseResponse;
import com.atticket.common.response.SampleDto;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
	/**
	 * 예약조회
	 * */
	@GetMapping("")
	public BaseResponse<SampleDto> getReservations() {
		return ok(SampleDto.builder().content("hi").build());
	}

	/**
	 * 예약하기
	 */
	@PostMapping("")
	public BaseResponse<SampleDto> postReservation() {
		return ok(SampleDto.builder().content("hi").build());
	}
}
