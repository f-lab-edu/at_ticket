package com.atticket.reservation.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Status {

	RESERVED("예약중"),
	CANCELLED("예약 취소");

	private final String status;

}
