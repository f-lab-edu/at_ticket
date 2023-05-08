package com.atticket.product.controller;

import static com.atticket.common.response.BaseResponse.ok;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atticket.common.response.BaseResponse;
import com.atticket.product.dto.request.RegisterShowReqDto;
import com.atticket.product.dto.response.GetRemainSeatsCntResDto;
import com.atticket.product.dto.response.GetRemainSeatsResDto;
import com.atticket.product.dto.service.GetRemainSeatCntSvcDto;
import com.atticket.product.service.ShowSeatService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//좌석 조회
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/shows")
public class ShowController {

	private final ShowSeatService showSeatService;

	//공연의 남은 좌석 조회
	@GetMapping("/{showId}/seats")
	public BaseResponse<GetRemainSeatsResDto> getRemainSeats(@PathVariable("showId") String id) {

		return ok(GetRemainSeatsResDto.builder().showSeats(List.of(
			GetRemainSeatsResDto.ShowSeat.builder()

				.id(1L)
				.space("1층")
				.locX("12")
				.locY("22")
				.row("T행")
				.rowNum(1)
				.grade("VIP")
				.price(120000)
				.build(),
			GetRemainSeatsResDto.ShowSeat.builder()
				.id(2L)
				.space("1층")
				.locX("15")
				.locY("25")
				.row("T행")
				.rowNum(2)
				.grade("VIP")
				.price(120000)
				.build(),
			GetRemainSeatsResDto.ShowSeat.builder()
				.id(3L)
				.space("1층")
				.locX("18")
				.locY("28")
				.row("T행")
				.rowNum(3)
				.grade("VIP")
				.price(120000)
				.build()
		)).build());
	}

	//공연의 남은 좌석수 조회
	@GetMapping("/{showId}/seats/count")
	public BaseResponse<GetRemainSeatsCntResDto> getRemainSeatsCnt(@PathVariable("showId") Long showId) {

		log.debug("getRemainSeatsCnt - showId : " + showId);

		//등급별 남은 좌석 조회
		List<GetRemainSeatCntSvcDto> remainSeatCnts = showSeatService.getRemainSeatCntByShowId(showId);

		List<GetRemainSeatsCntResDto.RemainSeat> remainSeatsList = remainSeatCnts.stream()
			.map(cntDto -> GetRemainSeatsCntResDto.RemainSeat.builder()
				.showId(showId)
				.gradeNm(cntDto.getGradeNm())
				.gradeId(cntDto.getGradeId())
				.cnt(cntDto.getSeatCnt())
				.build())
			.collect(Collectors.toList());

		return ok(
			GetRemainSeatsCntResDto.construct(remainSeatsList)
		);
	}

	//공연 등록
	@PostMapping("")
	public BaseResponse registerShow(@RequestBody RegisterShowReqDto registerShowReqDto) {
		log.info("registerShow - registerShowRequest : " + registerShowReqDto);
		return ok();
	}

}
