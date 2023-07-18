package com.atticket.user.client.dto.request;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class KeycloakRegisterUserReqDto {

	private String username;

	private String email;

	private Boolean emailVerified = true;

	private String firstName;

	private Boolean enabled = true;

	// private List<Credential> credentials;

	@AllArgsConstructor
	private static class Credential {
		String value;
	}

	public static List<Credential> parseToCredentials(String password) {
		List<Credential> credentials = new ArrayList<>();
		Credential credential = new Credential(password);
		credentials.add(credential);
		return credentials;
	}
}
