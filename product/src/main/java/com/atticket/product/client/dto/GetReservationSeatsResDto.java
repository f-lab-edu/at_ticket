package com.atticket.product.client.dto;

import java.beans.ConstructorProperties;
import java.util.List;

import lombok.Getter;

@Getter
public class GetReservationSeatsResDto {

	public final List<ReservedSeatsDto> reservedSeats;

	@ConstructorProperties({"reservedSeats"})
	public GetReservationSeatsResDto(List<ReservedSeatsDto> reservedSeats) {
		this.reservedSeats = reservedSeats;
	}

	//예약된 좌석 리스트
	@Getter
	public static class ReservedSeatsDto {

		//예약좌석 id
		private final Long id;

		//공연 id
		private final Long showId;

		//좌석 id
		private final Long seatId;

		@ConstructorProperties({"id", "showId", "seatId"})
		public ReservedSeatsDto(Long id, Long showId, Long seatId) {
			this.id = id;
			this.showId = showId;
			this.seatId = seatId;
		}

	}

}
