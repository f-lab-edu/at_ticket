package com.atticket.product.dto.response;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.util.CollectionUtils;

import com.atticket.product.domain.Show;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetShowsResDto {
	//일자별 공연 조회 ResponseDto

	private final List<ShowDto> shows;

	public static GetShowsResDto construct(List<Show> shows) {
		if (CollectionUtils.isEmpty(shows)) {
			return null;
		}
		return new GetShowsResDto(shows.stream().map(ShowDto::construct).collect(Collectors.toList()));
	}

	@Getter
	@Builder
	private static class ShowDto {

		//공연 id
		private final Long id;
		//session
		private final int session;
		//공연 시간
		private final LocalTime time;

		private static ShowDto construct(Show show) {
			return ShowDto.builder()
				.id(show.getId())
				.session(show.getSession())
				.time(show.getTime())
				.build();
		}
	}
}
