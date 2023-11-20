package com.atticket.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginUserReqDto {
	private String username;
	private String password;
}
