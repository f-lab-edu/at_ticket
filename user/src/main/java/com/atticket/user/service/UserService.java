package com.atticket.user.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.atticket.common.response.BaseException;
import com.atticket.user.client.client.KeycloakFeignClient;
import com.atticket.user.client.dto.request.GetAdminAccessTokenReqDto;
import com.atticket.user.client.dto.request.KeycloakRegisterUserReqDto;
import com.atticket.user.client.dto.response.GetAdminAccessTokenResDto;
import com.atticket.user.dto.request.RegisterUserReqDto;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

	private final KeycloakFeignClient keycloakFeignClient;

	private KeycloakAccessToken keycloakAccessToken = new KeycloakAccessToken();

	public void registerUser(RegisterUserReqDto reqDto) {
		// Admin Token 체크
		if (keycloakAccessToken.isExpired()) {
			this.setAdminToken();
		}

		try {
			keycloakFeignClient.registerUser("bearer " + keycloakAccessToken.token,
				KeycloakRegisterUserReqDto.construct(reqDto.getUserId(),
					reqDto.getPassword(), reqDto.getEmail(), reqDto.getName())
			);
		} catch (FeignException e) {
			throw new BaseException(400, e.getMessage());
		}
	}

	// AdminToken 셋팅
	private void setAdminToken() {
		// AdminToken 발급
		GetAdminAccessTokenResDto resDto = keycloakFeignClient.getAdminAccessToken(
			new GetAdminAccessTokenReqDto()
		);

		log.info("getAdminAccessToken");

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
