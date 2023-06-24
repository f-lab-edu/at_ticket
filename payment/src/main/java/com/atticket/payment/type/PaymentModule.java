package com.atticket.payment.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PaymentModule {

	NAVER_PAY("네이버페이"),
	KAKAO_PAY("카카오페이");

	private final String payment;
}
