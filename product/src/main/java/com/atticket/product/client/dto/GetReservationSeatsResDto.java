package com.atticket.product.client.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class GetReservationSeatsResDto {

	//예약된 좌석 리스트
	public final List<ReservedSeatsDto> reservedSeats;

	@Getter
	@ToString
	@AllArgsConstructor
	public static class ReservedSeatsDto {

		//예약좌석 id
		private final Long id;

		//공연 id
		private final Long showId;

		//좌석 id
		private final Long seatId;

	}

}
