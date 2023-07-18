package com.atticket.user.client.dto.request;

import lombok.Getter;

@Getter
public class GetAdminAccessTokenReqDto {

	private String grant_type = "password";

	private String client_id = "admin-cli";

	private String username = "admin";

	private String password = "atticket";
}
