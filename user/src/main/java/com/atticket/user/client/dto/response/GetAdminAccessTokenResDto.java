package com.atticket.user.client.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetAdminAccessTokenResDto {

	private String access_token;

	private int expires_in;

	private int refresh_expires_in;

	private String refresh_token;

	private String token_type;
}
