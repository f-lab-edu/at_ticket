package com.atticket.user.client.dto.request;

import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class KeycloakRegisterUserReqDto {

	private String username;

	private String email;

	private final Boolean emailVerified = true;

	private String firstName;

	private final Boolean enabled = true;

	private List<Credential> credentials;

	public KeycloakRegisterUserReqDto(String username, String password, String email, String name) {
		List<Credential> credentials = Arrays.asList(new Credential(password));
		this.username = username;
		this.email = email;
		this.firstName = name;
		this.credentials = credentials;
	}

	@AllArgsConstructor
	@Getter
	private class Credential {
		private String value;
	}
}
