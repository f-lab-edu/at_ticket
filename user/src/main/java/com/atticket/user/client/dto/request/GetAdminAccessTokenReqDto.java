package com.atticket.user.client.dto.request;

import lombok.Getter;

@Getter
public class GetAdminAccessTokenReqDto {

	private String grant_type = "password";

	private String client_id = "admin-cli";

	private String username;

	private String password;

	public GetAdminAccessTokenReqDto(String username, String password) {
		this.username = username;
		this.password = password;
	}
}
