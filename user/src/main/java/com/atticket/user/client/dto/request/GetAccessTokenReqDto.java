package com.atticket.user.client.dto.request;

import lombok.Getter;

@Getter
public class GetAccessTokenReqDto {

	private String grant_type = "password";

	private String client_id;

	private String client_secret;

	private String username;

	private String password;

	public GetAccessTokenReqDto(String clientId, String clientSecret, String username, String password) {
		this.client_id = clientId;
		this.client_secret = clientSecret;
		this.username = username;
		this.password = password;
	}
}
