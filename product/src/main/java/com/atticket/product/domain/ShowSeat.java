package com.atticket.product.domain;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

/**
 * 좌석 -등급 매핑
 */
@Getter
@Builder
public class ShowSeat {

	//공연좌석 id
	private Long id;

	//공연
	private Show show;

	//좌석 리스트
	private List<Seat> seats;

	//등급 id
	private Grade grade;

	public void setId(Long id) {
		this.id = id;
	}
}
