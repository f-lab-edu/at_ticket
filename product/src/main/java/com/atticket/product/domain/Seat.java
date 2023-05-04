package com.atticket.product.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Seat {

	//좌석 id
	private Long id;

	//공간 (e.g. 1층, 2층, .../ A존, B존, ...)
	private String space;

	//x 좌표
	private String locX;

	//y 좌표
	private String locY;

	//열
	private String row;

	//열 번호
	private int rowNum;

	//공연홀 id
	private String hallId;

}
