package com.atticket.user.client.dto.request;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegisterUserReqDto {
	String username;
	String email;
	Boolean emailVerified = true;
	String firstName;
	Boolean enable = true;
	String password;
	List<Credential> credentials;

	public class Credential {
		String value;
	}
}
