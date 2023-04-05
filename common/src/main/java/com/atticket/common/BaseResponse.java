package com.atticket.common;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"code", "message", "data"})
public class BaseResponse<T> {
	private final int code;
	private final String message;
	private final T data;

	// success response
	public BaseResponse(T data) {
		this(BaseStatus.SUCCESS.getCode(), BaseStatus.SUCCESS.getMessage(), data);
	}

}
