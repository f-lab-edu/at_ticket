package com.atticket.user.client.dto.request;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class KeycloakRegisterUserReqDto {

	private String username;

	private String email;

	private final Boolean emailVerified = true;

	private String firstName;

	private final Boolean enabled = true;

	private List<Credential> credentials;

	private KeycloakRegisterUserReqDto(String userId, String email, String name, List<Credential> credentials) {
		this.username = userId;
		this.email = email;
		this.firstName = name;
		this.credentials = credentials;
	}

	public static KeycloakRegisterUserReqDto construct(String userId, String password, String email, String name) {
		List<Credential> credentials = parseToCredentials(password);
		return new KeycloakRegisterUserReqDto(userId, email, name, credentials);
	}

	@RequiredArgsConstructor
	@Getter
	private static class Credential {
		private final String value;
	}

	private static List<Credential> parseToCredentials(String password) {
		return Arrays.asList(new Credential(password));
	}
}
