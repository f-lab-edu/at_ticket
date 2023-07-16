package com.atticket.payment.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum LinkedPlatformType {

	I_AM_PORT("아임포트"),
	NICE_PAY("나이스페이");

	private final String name;

}
