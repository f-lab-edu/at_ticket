package com.atticket.product.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Seat {

	//좌석 id
	// private Long id;

	// 좌석 공간 (e.g. 1층, 2층, .../ A존, B존, ...)
	private String space;

	// 좌석 x 좌표
	private String locX;

	// 좌석 y 좌표
	private String locY;

	//열
	private String row;

	//열 번호
	private int rowNum;

	//공연 홀
	private String hallId;

}
