package com.atticket.product.dto.response;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.util.CollectionUtils;

import com.atticket.product.dto.service.GetShowSeatsSvcDto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetShowSeatsResDto {

	private final List<ShowSeatDto> seats;

	public static GetShowSeatsResDto construct(List<GetShowSeatsSvcDto> getShowSeatsSvcDtos) {
		if (CollectionUtils.isEmpty(getShowSeatsSvcDtos)) {
			return null;
		}
		List<ShowSeatDto> showSeatDtos = new ArrayList<>();
		getShowSeatsSvcDtos.forEach(getShowSeatsSvcDto -> {
			showSeatDtos.addAll(ShowSeatDto.construct(getShowSeatsSvcDto));
		});
		return new GetShowSeatsResDto(showSeatDtos);
	}

	@Getter
	@Builder
	private static class ShowSeatDto {

		private final Long id;

		// 좌석 공간 (e.g. 1층, 2층, .../ A존, B존, ...)
		private final String space;

		// 좌석 x 좌표
		private final String locX;

		// 좌석 y 좌표
		private final String locY;

		// 행
		private final String row;

		// 행 번호
		private final int rowNum;

		//등급
		private final String grade;

		//가격
		private final int price;

		private static List<ShowSeatDto> construct(GetShowSeatsSvcDto getShowSeatsSvcDto) {
			return getShowSeatsSvcDto.getSeats().stream().map(seat ->
				ShowSeatDto.builder()
					.id(seat.getId())
					.space(seat.getSpace())
					.locX(seat.getLocX())
					.locY(seat.getLocY())
					.row(seat.getSeatRow())
					.rowNum(seat.getSeatRowNum())
					.grade(getShowSeatsSvcDto.getGrade().getType())
					.price(getShowSeatsSvcDto.getGrade().getPrice())
					.build()).collect(Collectors.toList());
		}
	}

}
