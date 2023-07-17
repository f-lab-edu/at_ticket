package com.atticket.user.client.dto.response;

import lombok.Getter;

@Getter
public class GetAdminAccessTokenResDto {

	private String access_token;

	private int expires_in;

	private int refresh_expires_in;

	private int refresh_token;

	private String token_type;
}
