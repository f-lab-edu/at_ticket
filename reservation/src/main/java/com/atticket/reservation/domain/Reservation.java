package com.atticket.reservation.domain;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Reservation {

	//예약 id
	private Long id;

	//유저 id
	private Long userId;

	//좌석 Id
	private String seatId;

	//변경사항
	//private modifyType modifyData;

	//예약 시간
	private LocalDateTime time;

}
