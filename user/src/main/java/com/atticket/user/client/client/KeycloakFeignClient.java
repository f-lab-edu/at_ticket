package com.atticket.user.client.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.atticket.user.client.dto.request.GetAdminAccessTokenReqDto;
import com.atticket.user.client.dto.request.RegisterUserReqDto;

@FeignClient(name = "keycloakFeignClient", url = "${keycloak.url}")
public interface KeycloakFeignClient {
	@PostMapping(value = "/admin/realms/${keycloak.realm}/users")
	Object registerUser(@RequestHeader("Authorization") String adminAccessToken,
		@RequestBody RegisterUserReqDto reqDto);

	@PostMapping(value = "/realms/master/protocol/openid-connect/token",
		consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	Object getAdminAccessToken(@ModelAttribute("reqDto") GetAdminAccessTokenReqDto reqDto);
}
