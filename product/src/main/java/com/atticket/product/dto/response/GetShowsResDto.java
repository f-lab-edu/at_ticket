package com.atticket.product.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetShowsResDto {
	//일자별 공연 조회 ResponseDto

	private final List<Session> session;

	@Getter
	@Builder
	public static class Session {

		//공연 ID
		private final Long id;

		//공연 시간
		private final LocalDateTime time;
	}
}
