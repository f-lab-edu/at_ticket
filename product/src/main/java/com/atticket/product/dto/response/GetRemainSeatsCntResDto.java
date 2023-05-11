package com.atticket.product.dto.response;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.util.ObjectUtils;

import com.atticket.product.dto.service.GetRemainSeatCntSvcDto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GetRemainSeatsCntResDto {
	//회차별 공연 잔여 좌석수 조회 ResponseDto

	private final List<RemainSeat> remainSeats;

	public static GetRemainSeatsCntResDto construct(List<GetRemainSeatCntSvcDto> remainSeats) {
		if (ObjectUtils.isEmpty(remainSeats)) {
			return null;
		}
		return new GetRemainSeatsCntResDto(
			remainSeats.stream().map(
				x -> RemainSeat.builder()
					.showId(x.getShowId())
					.gradeId(x.getGradeId())
					.gradeNm(x.getGradeNm())
					.seatCnt(x.getSeatCnt())
					.build()
			).collect(Collectors.toList()));
	}

	@Getter
	@Builder
	//잔여좌석
	public static class RemainSeat {

		//공연 Id
		private final Long showId;
		//좌석 등급
		private final Long gradeId;
		//좌석 등급 이름
		private final String gradeNm;
		//남은 좌석 수
		private final int seatCnt;
	}
}
