package com.atticket.user.controller;

import static com.atticket.common.response.BaseResponse.ok;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atticket.common.response.BaseResponse;
import com.atticket.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@GetMapping("/users")
	public BaseResponse<Object> getAdminToken() {
		userService.getAdminToken();
		return ok();
	}
}
