package com.atticket.payment.client.dto.reponse;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class GetIamPortAccessTokenResDto {

	public String code;

	public String message;

	public ResponseDto response;

	@Getter
	@RequiredArgsConstructor
	public static class ResponseDto {

		private final String access_token;

		private final String now;

		private final String expired_at;

	}

}
