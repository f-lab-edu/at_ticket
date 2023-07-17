package com.atticket.user.client.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class GetAdminAccessTokenReqDto {

	String client_id = "admin-cli";

	String username;

	String password;

	String grant_type = "password";
}
