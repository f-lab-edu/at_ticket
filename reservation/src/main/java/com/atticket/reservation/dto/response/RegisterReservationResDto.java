package com.atticket.reservation.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RegisterReservationResDto {

	private Long reservationId;

	public static RegisterReservationResDto construct(Long reservationId) {
		return new RegisterReservationResDto(reservationId);
	}
}
