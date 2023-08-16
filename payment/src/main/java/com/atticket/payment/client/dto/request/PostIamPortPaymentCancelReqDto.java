package com.atticket.payment.client.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostIamPortPaymentCancelReqDto {

	// 거래 ID
	private final String imp_uid;

	//가맹점 주문번호 (imp_uid 누락시 필수)
	private final String merchant_uid;

	//취소 요청금액(누락시 전액취소)
	private final Double amount;

	//취소요청금액 중 면세금액 (누락되면 0원처리)
	private final Double tax_free;

	//부가세 지정(기본값: null) 결제 시 부가세를 지정했던 경우 필수 입력
	private final Double vat_amount;

	//현재시점의 취소 가능한 잔액
	private final Double checksum;

	//취소사유
	private final String reason;

	//환불계좌 예금주(가상계좌 취소 시 필수)
	private final String refund_holder;

	//환불계좌 은행코드 (은행코드표) (가상계좌 취소 시 필수)
	private final String refund_bank;

	//환불계좌 계좌번호 (가상계좌 취소 시 필수)
	private final String refund_account;

	//환불계좌 예금주 연락처
	private final String refund_tel;

}
