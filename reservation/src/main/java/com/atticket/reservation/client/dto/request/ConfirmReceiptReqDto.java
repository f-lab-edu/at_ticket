package com.atticket.reservation.client.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ConfirmReceiptReqDto {

	private String paymentId;
	private Long reservationId;
	private int amount;
}
