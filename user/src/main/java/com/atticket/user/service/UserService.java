package com.atticket.user.service;

import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
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

	@Value("${keycloak.admin.username}")
	private String KEYCLOAK_ADMIN_USERNAME;

	@Value("${keycloak.admin.password}")
	private String KEYCLOAK_ADMIN_PASSWORD;

	private KeycloakAccessToken keycloakAccessToken = new KeycloakAccessToken();

	public void registerUser(RegisterUserReqDto reqDto) {

		try {
			// Admin Token 체크
			if (keycloakAccessToken.isExpired()) {
				this.setAdminToken();
			}

			Object res = keycloakFeignClient.registerUser(
				"bearer " + keycloakAccessToken.token,
				new KeycloakRegisterUserReqDto(reqDto.getUsername(), reqDto.getPassword(),
					reqDto.getEmail(), reqDto.getName())
			);

			if (!Objects.isNull(res)) {
				throw new BaseException(400, res.toString());
			}

		} catch (FeignException e) {
			throw new BaseException(400, e.getMessage());
		}
	}

	// AdminToken 셋팅
	private void setAdminToken() {
		// AdminToken 발급
		GetAdminAccessTokenResDto resDto = keycloakFeignClient.getAdminAccessToken(
			new GetAdminAccessTokenReqDto(KEYCLOAK_ADMIN_USERNAME, KEYCLOAK_ADMIN_PASSWORD)
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
