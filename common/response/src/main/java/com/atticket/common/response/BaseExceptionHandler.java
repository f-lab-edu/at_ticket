package com.atticket.common.response;

import static com.atticket.common.response.BaseResponse.bindError;
import static com.atticket.common.response.BaseResponse.error;
import static com.atticket.common.response.BaseResponse.paramError;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MissingServletRequestParameterException;
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
		return error(baseException.getBaseStatus());
	}

	// Exception 발생시 핸들러
	@ExceptionHandler(Exception.class)
	public BaseResponse<BaseStatus> handleException(Exception exception) {
		log.error(exception.getClass().toString());
		log.error(BaseStatus.UNEXPECTED_ERROR.getMessage(), exception);
		return error(BaseStatus.UNEXPECTED_ERROR);
	}

	@ExceptionHandler(BindException.class)
	public BaseResponse handleBindException(BindException bindException) {
		log.error(bindException.getMessage(), bindException);
		log.error(bindException.getClass().getName());
		for (FieldError e : bindException.getFieldErrors()) {
			System.out.println(e.getCode());
		}
		List<String> errMsgList = bindException.getFieldErrors()
			.stream()
			.map(error -> {
				System.out.println(error.getCode());
				if (Objects.equals(error.getCode(), "typeMismatch")) {
					return error.getField() + "는 " + Objects.requireNonNull(
						bindException.getFieldType(error.getField())).getName()
						+ " 타입으로 입력해주세요.";
				} else {
					return error.getDefaultMessage();
				}
			})
			.collect(Collectors.toList());
		return bindError("validation 에러입니다.", errMsgList);
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public BaseResponse handleMissingServletRequestParameterException(MissingServletRequestParameterException exception) {

		return paramError(exception.getParameterName()+"를 입력해주세요");
	}
}
