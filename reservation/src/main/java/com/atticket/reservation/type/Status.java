package com.atticket.reservation.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Status {

	WAIT_PAY("결제 대기"),
	RESERVED("예약 완료"),
	CANCELLED("예약 취소");

	private final String status;

}
