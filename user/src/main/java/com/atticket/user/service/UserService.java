package com.atticket.user.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.atticket.user.client.client.KeycloakFeignClient;
import com.atticket.user.client.dto.request.GetAdminAccessTokenReqDto;
import com.atticket.user.client.dto.request.KeycloakRegisterUserReqDto;
import com.atticket.user.client.dto.response.GetAdminAccessTokenResDto;
import com.atticket.user.dto.request.RegisterUserReqDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final KeycloakFeignClient keycloakFeignClient;

	private KeycloakAccessToken keycloakAccessToken = new KeycloakAccessToken();

	public void registerUser(RegisterUserReqDto reqDto) {
		if (keycloakAccessToken.isExpired()) {
			this.setAdminToken();
		}

		System.out.println(reqDto.getUserId());
		System.out.println(reqDto.getPassword());
		System.out.println(reqDto.getName());
		System.out.println(reqDto.getEmail());

		KeycloakRegisterUserReqDto a = KeycloakRegisterUserReqDto.builder()
			.username(reqDto.getUserId())
			.firstName(reqDto.getName())
			.email(reqDto.getEmail())
			// .credentials(KeycloakRegisterUserReqDto.parseToCredentials(reqDto.getPassword()))
			.build();
		System.out.println(a.getEnabled());
		System.out.println(a.getEmailVerified());

		keycloakFeignClient.registerUser("bearer " + keycloakAccessToken.token,
			a
		);

	}

	private void setAdminToken() {
		GetAdminAccessTokenResDto resDto = keycloakFeignClient.getAdminAccessToken(
			new GetAdminAccessTokenReqDto()
		);

		System.out.println("setAdminToken : " + resDto.getAccess_token());

		keycloakAccessToken.token = resDto.getAccess_token();
		keycloakAccessToken.expireTime = LocalDateTime.now().plusSeconds(resDto.getExpires_in() - 10);

	}

	class KeycloakAccessToken {
		private String token;
		private LocalDateTime expireTime = LocalDateTime.now();

		private Boolean isExpired() {
			return this.expireTime.isBefore(LocalDateTime.now());
		}
	}
}
