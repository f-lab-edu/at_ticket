package com.atticket.common;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<T> {
	private final int code;
	private final String message;
	private final T data;

	// success response
	public BaseResponse(T data) {
		this(BaseStatus.SUCCESS.getCode(), BaseStatus.SUCCESS.getMessage(), data);
	}

	// error response
	public BaseResponse(BaseStatus status) {
		this(status.getCode(), status.getMessage(), null);
	}
}
