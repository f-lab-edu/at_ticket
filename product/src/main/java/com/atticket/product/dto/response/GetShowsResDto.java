package com.atticket.product.dto.response;

import java.time.LocalTime;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetShowsResDto {
	//일자별 공연 조회 ResponseDto

	private final List<Show> shows;

	@Getter
	@Builder
	public static class Show {

		//공연 Id
		private Long id;

		//session
		private int session;

		//공연 시간
		private LocalTime time;
	}
}
