package com.atticket.user.dto.request;

import lombok.Getter;

@Getter
public class RegisterUserReqDto {
	private String username;
	private String password;
	private String email;
	private String name;
	private String phone;
}
