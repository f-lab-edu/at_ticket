package com.atticket.payment.dto.request;

import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.atticket.payment.type.LinkedPlatformType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@Validated
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class PostCancelPaymentReqDto {

	//결제 번호
	@NotNull
	private final String paymentId;

	//주문 번호
	@NotNull
	private final String orderId;

	//취소 금액
	private final Double amount;

	//취소요청금액 중 면세금액
	private final Double taxFree;

	//취소 사유
	private final String reason;

	//환불계좌 예금주
	private final String refundHolder;

	// 환불계좌 예금주 연락처
	private final String refundTel;

	//환불계좌 은행
	private final String refundBank;

	//환불계좌 계좌번호
	private final String refundAccount;

	//결제 연동 타입
	@NotNull
	private final LinkedPlatformType linkedPlatform;

	//(아임포트 판매자) API 키
	private final String impKey;

	//(아임포트 판매자) 비밀 키
	private final String impSecret;

}
