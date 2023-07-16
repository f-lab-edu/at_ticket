package com.atticket.payment.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PgProviderType {

	KAKAO_PAY("카카오페이"),
	NAVER_PAY("네이버페이");

	private final String name;

}
