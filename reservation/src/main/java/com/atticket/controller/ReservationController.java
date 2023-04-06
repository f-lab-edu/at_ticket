package com.atticket.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atticket.dto.SampleDto;

@RestController
@RequestMapping("/reservation")
public class ReservationController {
	/**
	 * 예약조회
	 * */
	@GetMapping("")
	public SampleDto getReservation() {
		SampleDto sampleDto = new SampleDto();
		sampleDto.setContent("hi");
		return sampleDto;
	}

	/**
	 * 예약하기
	 */
	@PostMapping("")
	public SampleDto postReservation() {
		SampleDto sampleDto = new SampleDto();
		sampleDto.setContent("hi");
		return sampleDto;
	}
}
