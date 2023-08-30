package com.atticket.payment.dto.request;

import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Validated
public class ConfirmReceiptReqDto {

	//결제 번호 (imp_xxxxxxx)
	@NotNull
	private String paymentId;

	@NotNull
	private Long reservationId;

	@NotNull
	private Long amount;
}
