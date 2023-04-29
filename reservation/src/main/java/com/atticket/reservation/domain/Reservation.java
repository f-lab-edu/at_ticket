package com.atticket.reservation.domain;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Reservation {

	private Long id;
	private Long userId;
	private String seatId;
	//변경사항
	//private modifyType modifyData;
	private LocalDateTime time;

}
