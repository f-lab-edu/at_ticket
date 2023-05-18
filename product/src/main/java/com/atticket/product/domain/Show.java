package com.atticket.product.domain;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Show {

	//공연 id
	private Long id;

	//시간
	private LocalTime time;

	//회차
	private int session;

	//일자
	private LocalDate date;

	//상품 id
	private Long productId;


	//공연홀 id
	private Long hallId;

	public void setId(Long id) {
		this.id = id;
	}

}
