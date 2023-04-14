package com.atticket.common.response;

import static com.atticket.common.response.BaseResponse.fail;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class BaseExceptionHandler {

	// BaseException 발생시 핸들러
	@ExceptionHandler(BaseException.class)
	public BaseResponse<BaseStatus> handleBaseException(BaseException baseException) {
		log.error(baseException.getBaseStatus().getMessage(), baseException);
		return fail(baseException.getBaseStatus());
	}

	// Exception 발생시 핸들러
	@ExceptionHandler(Exception.class)
	public BaseResponse<BaseStatus> handleException(Exception exception) {
		log.error(BaseStatus.UNEXPECTED_ERROR.getMessage(), exception);
		return fail(BaseStatus.UNEXPECTED_ERROR);
	}
}
