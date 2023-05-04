package com.atticket.product.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
/**
 * 좌석 -등급 매핑
 */

public class ShowSeat {

	//공연좌석 id
	private Long id;

	//공연 id
	private Long showId;

	//좌석 리스트
	private String seatList;

	//등급 id
	private Long gradeId;

	//상품 id
	private Long productId;

}
