package com.atticket.reservation.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReservedSeat {

	//예약좌석 id
	private Long id;

	//공연 id
	private Long showId;

	//좌석 id
	private Long seatId;

}
