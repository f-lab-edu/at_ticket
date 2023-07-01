package com.atticket.common.response;

import static com.atticket.common.response.BaseResponse.error;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.fasterxml.jackson.databind.JsonMappingException;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class BaseExceptionHandler {

	// BaseException 발생시 핸들러
	@ExceptionHandler(BaseException.class)
	public BaseResponse<Object> handleBaseException(BaseException exception) {
		log.error(exception.getClass().getName(), exception);
		return error(exception.getCode(), exception.getMessage());
	}

	// Exception 발생시 핸들러
	@ExceptionHandler(Exception.class)
	public BaseResponse<Object> handleException(Exception exception) {
		log.error(exception.getClass().getName(), exception);
		return error(BaseStatus.UNEXPECTED_ERROR.getCode(), BaseStatus.UNEXPECTED_ERROR.getMessage());
	}

	// @ModelAttribute BindException 발생시 핸들러
	@ExceptionHandler(BindException.class)
	public BaseResponse<List<String>> handleBindException(BindException exception) {
		log.error(exception.getClass().getName(), exception);
		List<String> errMsgList = exception.getFieldErrors()
			.stream().map(error -> {
				if (Objects.requireNonNull(error.getDefaultMessage()).contains("BaseException: ")) {
					return error.getField() + " - " + error.getDefaultMessage().split("BaseException: ")[1];
				}
				return error.getField() + " - " + error.getCode();
			}).collect(Collectors.toList());
		return error(400, "validation 에러입니다.", errMsgList);
	}

	// @RequestParam MissingServletRequestParameterException 발생시 핸들러
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public BaseResponse<Object> handleMissingServletRequestParameterException(
		MissingServletRequestParameterException exception) {
		log.error(exception.getClass().getName(), exception);
		List<String> errMsgList = new ArrayList<>(List.of(exception.getParameterName() + " - NotNull"));
		return error(400, "validation 에러입니다.", errMsgList);
	}

	// @RequestParam MethodArgumentTypeMismatchException 발생시 핸들러
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public BaseResponse<List<String>> handleMethodArgumentTypeMismatchException(
		MethodArgumentTypeMismatchException exception) {
		log.error(exception.getClass().getName(), exception);
		List<String> errMsgList = new ArrayList<>(List.of(exception.getName() + " - typeMismatch"));
		return error(400, "validation 에러입니다.", errMsgList);
	}

	// @RequestBody validation 에러
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public BaseResponse<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
		log.error(exception.getClass().getName(), exception);
		Throwable cause = exception.getCause();
		List<String> errMsgList = new ArrayList<>();

		if (Objects.isNull(cause)) {
			return error(400, "RequestBody가 존재하지 않습니다.");
		}

		if (cause instanceof JsonMappingException) {
			JsonMappingException ife = (JsonMappingException)cause;
			StringBuilder field = new StringBuilder();

			for (int i = 0; i < ife.getPath().size(); i++) {
				String fieldName = ife.getPath().get(i).getFieldName();
				if (!Objects.isNull(fieldName)) {
					if (i != 0) {
						field.append(".");
					}
					field.append(fieldName);
				} else {
					int index = ife.getPath().get(i).getIndex();
					field.append("[").append(index).append("]");
				}

			}

			errMsgList.add(field + " - typeMismatch");
		}

		return error(400, "validation 에러입니다.", errMsgList);
	}
}
