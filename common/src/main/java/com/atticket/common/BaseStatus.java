package com.atticket.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BaseStatus {

	SUCCESS(200, "요청에 성공하셨습니다.");

	private final int code;
	private final String message;
}
