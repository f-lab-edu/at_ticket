package com.atticket.common.response;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BaseException extends RuntimeException {
	private final int code;
	private final String message;

	public BaseException(BaseStatus baseStatus) {
		this.code = baseStatus.getCode();
		this.message = baseStatus.getMessage();
	}

	public static void throwTypeMismatchIfNull(Object obj) {
		if (Objects.isNull(obj)) {
			throw new BaseException(400, "typeMismatch");
		}
	}
}
