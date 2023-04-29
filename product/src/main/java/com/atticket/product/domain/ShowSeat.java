package com.atticket.product.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
/**
 * 좌석 -등급 매핑
 */

public class ShowSeat {

	private Long id;
	private Long showId;
	private String seatList;
	private Long gradeId;
	private Long productId;

}
