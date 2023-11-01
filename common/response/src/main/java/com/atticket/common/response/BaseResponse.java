package com.atticket.common.response;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<T> implements Serializable {
	private final int code;
	private final String message;
	private final T data;

	private BaseResponse(int code, String message, T data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}

	// success response without data
	public static BaseResponse ok() {
		return new BaseResponse<>(BaseStatus.SUCCESS.getCode(), BaseStatus.SUCCESS.getMessage(), null);
	}

	// success response with data
	public static <T> BaseResponse<T> ok(T data) {
		if (Objects.isNull(data)) {
			return new BaseResponse<>(BaseStatus.NO_RESULT.getCode(), BaseStatus.NO_RESULT.getMessage(), null);
		}
		return new BaseResponse<>(BaseStatus.SUCCESS.getCode(), BaseStatus.SUCCESS.getMessage(), data);
	}

	// error response
	static BaseResponse error(int code, String message) {
		return new BaseResponse<>(code, message, null);
	}

	// error response with data
	static <T> BaseResponse<T> error(int code, String message, T data) {
		return new BaseResponse<>(code, message, data);
	}
}
