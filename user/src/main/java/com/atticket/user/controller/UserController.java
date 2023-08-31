package com.atticket.user.controller;

import static com.atticket.common.response.BaseResponse.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atticket.common.jwtmanager.JwtManager;
import com.atticket.common.jwtmanager.UserInfo;
import com.atticket.common.response.BaseResponse;
import com.atticket.user.client.dto.response.GetAccessTokenResDto;
import com.atticket.user.dto.request.LoginUserReqDto;
import com.atticket.user.dto.request.RegisterUserReqDto;
import com.atticket.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

	private final UserService userService;

	/**
	 * 회원가입
	 */
	@PostMapping("")
	public BaseResponse<Object> registerUser(@RequestBody RegisterUserReqDto reqDto) {
		userService.registerUser(reqDto);
		return ok();
	}

	/**
	 * 로그인
	 */
	@PostMapping("/token")
	public BaseResponse<GetAccessTokenResDto> loginUser(@RequestBody LoginUserReqDto reqDto) {
		return ok(userService.login(reqDto));
	}

	/**
	 * 유저 정보 조회
	 */
	@GetMapping("")
	public BaseResponse<UserInfo> getUser() {
		return ok(JwtManager.getUserInfo());
	}
}
