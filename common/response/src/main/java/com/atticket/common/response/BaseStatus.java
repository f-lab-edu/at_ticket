package com.atticket.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BaseStatus {

	SUCCESS(200, "요청에 성공하셨습니다."),
	TEST_ERROR(400, "테스트 에러메세지입니다."),
	UNEXPECTED_ERROR(500, "예상치 못한 에러가 발생했습니다.");

	private final int code;
	private final String message;
}
