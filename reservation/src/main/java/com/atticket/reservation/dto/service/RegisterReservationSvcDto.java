package com.atticket.reservation.dto.service;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegisterReservationSvcDto {

	private Long showId;

	private List<Long> seatIds;

}
