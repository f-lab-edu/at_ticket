package com.atticket.payment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ConfirmReceiptResDto {

	//결제 번호
	private final String paymentId;

	//주문 번호
	private final String orderId;

}
