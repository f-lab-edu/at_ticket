package com.atticket.product.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetShowListResDto {
	//일자별 공연 조회 ResponseDto

	private final List<ShowSeq> showSeq;

	@Getter
	@Builder
	public static class ShowSeq {

		//공연 ID
		private final String showId;

		//공연 시간
		private final String showTime;

	}

}
