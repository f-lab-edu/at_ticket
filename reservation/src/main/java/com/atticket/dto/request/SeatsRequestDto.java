package com.atticket.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeatsRequestDto {
	//좌석 조회 요청시 사용하는 RequestDTO입니다

	//공연 Id
	private String showId;

	//공연 - 날짜시간 (회차)id
	private String showSessionId;

}
