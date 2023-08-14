package com.atticket.payment.type;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PgProviderType {

	KAKAO_PAY("카카오페이", "kakaopay");

	private final String name;

	// iamport pg value
	private final String value;

	public static PgProviderType findByValue(String value) {
		return Arrays.stream(PgProviderType.values())
			.filter(type -> type.getValue().equals(value)).findAny().orElse(null);
	}
}
