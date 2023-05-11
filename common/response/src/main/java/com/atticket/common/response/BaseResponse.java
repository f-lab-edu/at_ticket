package com.atticket.common.response;

import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<T> {
	private final int code;
	private final String message;
	private final T data;

	private BaseResponse(int code, String message, T data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}

	// success response without data
	public static <T> BaseResponse<T> ok() {
		return new BaseResponse<>(BaseStatus.SUCCESS.getCode(), BaseStatus.SUCCESS.getMessage(), null);
	}

	// success response with data
	public static <T> BaseResponse<T> ok(T data) {
		if (ObjectUtils.isEmpty(data)) {
			return new BaseResponse<>(BaseStatus.NO_RESULT.getCode(), BaseStatus.NO_RESULT.getMessage(), null);
		}
		return new BaseResponse<>(BaseStatus.SUCCESS.getCode(), BaseStatus.SUCCESS.getMessage(), data);
	}

	// error response
	static <T> BaseResponse<T> error(BaseStatus status) {
		return new BaseResponse<>(status.getCode(), status.getMessage(), null);
	}

	// binding error response
	static <T> BaseResponse<T> bindError(String message, T data) {
		return new BaseResponse<>(400, message, data);
	}

	// binding error response
	static <T> BaseResponse<T> paramError(String message) {
		return new BaseResponse<>(400, message,null);
	}
}
