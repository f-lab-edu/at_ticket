package com.atticket.payment.client.dto.reponse;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
public class GetIamPortReceiptResDto {

	public String code;

	public String message;

	public ResponseDto response;

	@Getter
	@RequiredArgsConstructor
	@ToString
	public static class ResponseDto {

		//합계 금액
		private final Long amount;

		//구매자 주소
		private final String buyer_addr;

		//구매자 이메일
		private final String buyer_email;

		//구매자 이름
		private final String buyer_name;

		//구매자 우편 번호
		private final String buyer_postcode;

		//구매자 전화번호
		private final String buyer_tel;

		//취소 금액
		private final String cancel_amount;

		//취소 내역
		private final List<String> cancel_history;

		//취소 사유
		private final String cancel_reason;

		//취소 영수증 주소
		private final List<String> cancel_receipt_urls;

		//취소 일시
		private final String cancelled_at;

		private final String channel;

		//통화
		private final String currency;

		//결제 id
		private final String imp_uid;

		//주문 id
		private final String merchant_uid;

		//상품명
		private final String name;

		//결제 일시
		private final Long paid_at;

		//결제 방법
		private final String pay_method;

		private final String pg_id;

		//pg 제공자 (kakaopay..)
		private final String pg_provider;

		private final String pg_tid;

		//영수증 url
		private final String receipt_url;

		private final String started_at;

		private final String status;

	}

}
