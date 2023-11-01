package com.atticket.common.jwtmanager;

import java.io.Serializable;
import java.util.Map;

import lombok.Getter;

@Getter
public class UserInfo implements Serializable {

	String userId;

	String email;

	String name;

	String phone;

	public UserInfo(Map claims) {
		this.userId = (String)claims.get("sub");
		this.email = (String)claims.get("email");
		this.name = (String)claims.get("name");
		this.phone = (String)claims.get("phone_number");
	}
}
