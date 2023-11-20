package com.atticket.user.service;

import com.atticket.common.jwtmanager.JwtManager;
import com.atticket.common.jwtmanager.UserInfo;
import com.atticket.common.response.BaseException;
import com.atticket.user.client.client.KeycloakFeignClient;
import com.atticket.user.client.dto.request.GetAccessTokenReqDto;
import com.atticket.user.client.dto.request.KeycloakRegisterUserReqDto;
import com.atticket.user.client.dto.response.GetAccessTokenResDto;
import com.atticket.user.dto.request.LoginUserReqDto;
import com.atticket.user.dto.request.RegisterUserReqDto;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

	private final KeycloakFeignClient keycloakFeignClient;

	@Value("${keycloak.admin.username}")
	private String KEYCLOAK_ADMIN_USERNAME;

	@Value("${keycloak.admin.password}")
	private String KEYCLOAK_ADMIN_PASSWORD;

	private String KEYCLOAK_ADMIN_CLIENT_ID = "admin-cli";

	@Value("${keycloak.client.id}")
	private String KEYCLOAK_CLIENT_ID;

	@Value("${keycloak.client.secret}")
	private String KEYCLOAK_CLIENT_SECRET;

	private KeycloakAccessToken keycloakAccessToken = new KeycloakAccessToken();

	/**
	 * 로그인
	 */
	public GetAccessTokenResDto login(LoginUserReqDto reqDto) {
		try {
			return keycloakFeignClient.getUserAccessToken(
				new GetAccessTokenReqDto(KEYCLOAK_CLIENT_ID, KEYCLOAK_CLIENT_SECRET,
					reqDto.getUsername(), reqDto.getPassword())
			);
		} catch (FeignException e) {
			throw new BaseException(400, e.getMessage());
		}
	}

	/**
	 * 회원가입
	 */
	public void registerUser(RegisterUserReqDto reqDto) {

		try {
			// Admin Token 체크
			if (keycloakAccessToken.isExpired()) {
				this.setAdminToken();
			}

			Object res = keycloakFeignClient.registerUser(
				"bearer " + keycloakAccessToken.token,
				new KeycloakRegisterUserReqDto(reqDto.getUsername(), reqDto.getPassword(),
					reqDto.getEmail(), reqDto.getName(), Collections.singletonMap("phoneNumber", reqDto.getPhone()))
			);

			if (!Objects.isNull(res)) {
				throw new BaseException(400, res.toString());
			}

		} catch (FeignException e) {
			throw new BaseException(400, e.getMessage());
		}
	}

	public UserInfo getUserInfo(){
		return JwtManager.getUserInfo();
	}

	/**
	 * 관리자 Access Token 셋팅
	 */
	private void setAdminToken() {
		// 관리자 Access Token 발급
		GetAccessTokenResDto resDto = keycloakFeignClient.getAdminAccessToken(
			new GetAccessTokenReqDto(KEYCLOAK_ADMIN_CLIENT_ID, null,
				KEYCLOAK_ADMIN_USERNAME, KEYCLOAK_ADMIN_PASSWORD)
		);

		log.info("getAccessToken");

		keycloakAccessToken.token = resDto.getAccess_token();
		keycloakAccessToken.expireTime = LocalDateTime.now().plusSeconds(resDto.getExpires_in() - 10);

		log.info("setAccessToken");

	}

	class KeycloakAccessToken {
		private String token;
		private LocalDateTime expireTime = LocalDateTime.now();

		private Boolean isExpired() {
			return this.expireTime.isBefore(LocalDateTime.now());
		}
	}
}
