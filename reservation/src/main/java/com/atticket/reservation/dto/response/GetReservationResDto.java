package com.atticket.reservation.dto.response;

import java.io.Serializable;

import org.springframework.util.ObjectUtils;

import com.atticket.reservation.domain.Reservation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetReservationResDto implements Serializable {

	//예약 조회
	private final Reservation reservation;

	public static GetReservationResDto construct(Reservation reservation) {
		if (ObjectUtils.isEmpty(reservation)) {
			return null;
		}
		return new GetReservationResDto(reservation);

	}

}
