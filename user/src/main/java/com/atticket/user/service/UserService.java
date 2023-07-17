package com.atticket.user.service;

import org.springframework.stereotype.Service;

import com.atticket.user.client.client.KeycloakFeignClient;
import com.atticket.user.client.dto.request.GetAdminAccessTokenReqDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final KeycloakFeignClient keycloakFeignClient;

	public void getAdminToken() {

		System.out.println(keycloakFeignClient.getAdminAccessToken(
			GetAdminAccessTokenReqDto.builder()
				.username("admin")
				.password("atticket")
				.build()
		));

	}
}
