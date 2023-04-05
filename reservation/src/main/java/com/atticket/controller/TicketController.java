package com.atticket.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atticket.dto.SampleDto;

@RestController
@RequestMapping("/ticket")
public class TicketController {
	/**
	 * 티켓 검색
	 * */
	@GetMapping("")
	public SampleDto getTicket() {
		SampleDto sampleDto = new SampleDto();
		sampleDto.setContent("hi");
		return sampleDto;
	}

	/**
	 * 티켓 정보 조회
	 */
	@GetMapping("/{id}")
	public SampleDto getTicketInfo(@PathVariable("id") String id) {
		SampleDto sampleDto = new SampleDto();
		sampleDto.setContent(id);
		return sampleDto;
	}
}
