package com.atticket.show.controller;

import static com.atticket.common.response.BaseResponse.ok;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atticket.common.response.BaseResponse;
import com.atticket.show.dto.request.RegisterShowReqDto;
import com.atticket.show.dto.response.GetRemainSeatsCntResDto;
import com.atticket.show.dto.response.GetRemainSeatsResDto;

import lombok.extern.slf4j.Slf4j;

//좌석 조회
@Slf4j
@RestController
@RequestMapping("/shows")
public class ShowController {

	//공연의 남은 좌석 조회
	@GetMapping("/{showId}/seats")
	public BaseResponse<GetRemainSeatsResDto> getRemainSeats(@PathVariable("showId") String id) {

		return ok(GetRemainSeatsResDto.builder().showSeats(List.of(
			GetRemainSeatsResDto.ShowSeat.builder()
				.id("showSeat-1")
				.space("1층")
				.locX("12")
				.locY("22")
				.row("T열")
				.rowNum(1)
				.grade("VIP")
				.price(120000)
				.build(),
			GetRemainSeatsResDto.ShowSeat.builder()
				.id("showSeat-2")
				.space("1층")
				.locX("15")
				.locY("25")
				.row("T열")
				.rowNum(2)
				.grade("VIP")
				.price(120000)
				.build(),
			GetRemainSeatsResDto.ShowSeat.builder()
				.id("showSeat-3")
				.space("1층")
				.locX("18")
				.locY("28")
				.row("T열")
				.rowNum(3)
				.grade("VIP")
				.price(120000)
				.build()
		)).build());
	}

	//공연의 남은 좌석수 조회
	@GetMapping("/{showId}/seats/count")
	public BaseResponse<GetRemainSeatsCntResDto> getRemainSeatsCnt(@PathVariable("showId") String id) {

		log.info("getRemainSeatsCnt - showId : " + id);

		return ok(GetRemainSeatsCntResDto.builder()
			.remainSeatList(
				List.of(
					GetRemainSeatsCntResDto.RemainSeat.builder()
						.showId("1")
						.seatGrade("S")
						.remainSeatCnt(40)
						.build(),
					GetRemainSeatsCntResDto.RemainSeat.builder()
						.showId("1")
						.seatGrade("A")
						.remainSeatCnt(30)
						.build()
				)
			)
			.build());
	}

	//공연 등록
	@PostMapping("")
	public BaseResponse registerShow(@RequestBody RegisterShowReqDto registerShowReqDto) {
		log.info("registerShow - registerShowRequest : " + registerShowReqDto);
		return ok();
	}

}
