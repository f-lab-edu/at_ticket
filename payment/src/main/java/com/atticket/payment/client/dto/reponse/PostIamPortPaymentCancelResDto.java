package com.atticket.payment.client.dto.reponse;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
public class PostIamPortPaymentCancelResDto {

	public String code;

	public String message;

	public PostIamPortPaymentCancelResDto.ResponseDto response;

	public CancelHistory cancelHistory;

	@Getter
	@RequiredArgsConstructor
	@ToString
	public static class ResponseDto {

		//거래 id
		private final String imp_uid;

		//주문 id
		private final String merchant_uid;

		//결제 방법
		private final String pay_method;

		//pg 제공자 (kakaopay..)
		private final String pg_provider;

		private final String channel;
		private final String emb_pg_provider;
		private final String pg_tid;
		private final String pg_id;
		private final String escrow;
		private final String apply_num;
		private final String bank_code;
		private final String bank_name;
		private final String card_code;
		private final String card_name;
		private final String card_quota;
		private final String card_number;
		private final String card_type;
		private final String vbank_code;
		private final String vbank_name;
		private final String vbank_num;
		private final String vbank_holder;
		private final String vbank_date;
		private final String vbank_issued_at;

		//상품명
		private final String name;

		//합계 금액
		private final Long amount;

		//취소 금액
		private final String cancel_amount;

		//통화
		private final String currency;

		//구매자 이름
		private final String buyer_name;

		//구매자 이메일
		private final String buyer_email;

		//구매자 전화번호
		private final String buyer_tel;

		//구매자 주소
		private final String buyer_addr;

		//구매자 우편 번호
		private final String buyer_postcode;
		private final String custom_data;
		private final String user_agent;
		private final String status;
		private final String started_at;

		//결제 일시
		private final String paid_at;

		//실패 일시
		private final String failed_at;

		//취소 일시
		private final String cancelled_at;

		//실패 이유
		private final String fail_reason;

		//취소 이유
		private final String cancel_reason;

		//영수증 url
		private final String receipt_url;

		//취소 내역
		private final List<CancelHistory> cancel_history;

		//취소 url
		private final List<String> cancel_receipt_urls;
		private final String cash_receipt_issued;
		private final String customer_uid;
		private final String customer_uid_usage;

	}

	@Getter
	@RequiredArgsConstructor
	@ToString
	public static class CancelHistory {
		private final String pg_tid;
		private final double amount;
		private final String cancelled_at;
		private final String reason;
		private final String receipt_url;

	}

}
