package com.atticket.product.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SeatGrade {

	private Long id;
	private Long showId;
	private Long seatId;
	private Long gradeId;
	private Long productId;

}
