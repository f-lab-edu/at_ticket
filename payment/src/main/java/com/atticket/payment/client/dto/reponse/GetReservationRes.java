package com.atticket.payment.client.dto.reponse;

import java.beans.ConstructorProperties;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;

@Getter
public class GetReservationRes {
	private Long id;

	//유저 id
	private String userId;

	//예약 시간
	private LocalDateTime time;

	private List<Long> seatIds;

	//공연 id
	private Long showId;

	@ConstructorProperties({"id", "userId", "time", "showId", "seatIds"})
	public GetReservationRes(Long id, String userId, LocalDateTime time, Long showId, List<Long> seatIds) {
		this.id = id;
		this.userId = userId;
		this.showId = showId;
		this.time = time;
		this.seatIds = seatIds;
	}

}
