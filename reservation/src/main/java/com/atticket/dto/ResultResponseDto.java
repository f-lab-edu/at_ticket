package com.atticket.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ResultResponseDto {
	//추후 BaseRespone로 대체할 예정

	private int code;
	private String message;

	@Builder
	public ResultResponseDto(int code, String message) {
		this.code = code;
		this.message = message;
	}
}
