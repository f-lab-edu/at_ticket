package com.atticket.reservation.client.dto.response;

import java.beans.ConstructorProperties;

import lombok.Getter;

@Getter
public class ConfirmReceiptResDto {
	//결제 번호
	private String paymentId;

	//주문 번호
	private String orderId;

	@ConstructorProperties({"paymentId", "orderId"})
	public ConfirmReceiptResDto(String paymentId, String orderId) {
		this.paymentId = paymentId;
		this.orderId = orderId;
	}

}
