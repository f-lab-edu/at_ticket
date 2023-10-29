package com.atticket.payment.dto.request;

import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@Validated
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class ConfirmReceiptReqDto {

	//결제 번호 (imp_xxxxxxx)
	@NotNull
	private final String paymentId;

	@NotNull
	private final Long reservationId;

	@NotNull
	private final Long amount;
}
