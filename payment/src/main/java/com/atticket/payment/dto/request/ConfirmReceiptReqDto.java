package com.atticket.payment.dto.request;

import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.atticket.payment.type.LinkedPlatformType;
import com.atticket.payment.type.PgProviderType;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Validated
public class ConfirmReceiptReqDto {

	//결제 번호
	@NotNull
	private String paymentId;

	//주문 번호
	@NotNull
	private String orderId;

	//판매자 id
	private String sellerId;

	//구매자 id
	private String buyerId;

	//구매자 이름
	private String buyerName;

	//구매자 번호
	@NotNull
	private String buyerTel;

	//결제 금액
	@NotNull
	private Long amount;

	//결제 연동 타입
	@NotNull
	private LinkedPlatformType linkedPlatform;

	//pg
	@NotNull
	private PgProviderType pgProvider;

	//(아임포트 판매자) API 키
	private String impKey;

	//(아임포트 판매자) 비밀 키
	private String impSecret;

}
