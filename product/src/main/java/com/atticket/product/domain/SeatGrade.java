package com.atticket.product.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SeatGrade {

	private Long id;
	private String showId;
	private String seatId;
	private String gradeId;

}
