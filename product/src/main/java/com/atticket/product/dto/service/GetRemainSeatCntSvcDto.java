package com.atticket.product.dto.service;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class GetRemainSeatCntSvcDto {

	//공연 id
	private final Long showId;
	//등급 id
	private final Long gradeId;
	//등급 이름
	private final String gradeNm;
	//남은 좌석수
	private final int seatCnt;

}
