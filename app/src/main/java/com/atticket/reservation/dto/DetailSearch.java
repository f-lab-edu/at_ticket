package com.atticket.reservation.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DetailSearch {

	//공연 이름
	private String name;

	//공연 타입
	private int type;

	//공연 시작 기간
	private Date strDate;

}
