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

import lombok.extern.slf4j.Slf4j;

//좌석 조회
@Slf4j
@RestController
@RequestMapping("/shows")
public class ShowController {

	//공연별 남은 좌석 조회
	@GetMapping("/{showId}/seat/count")
	public BaseResponse<GetRemainSeatsCntResDto> getRemainSeatsCnt(@PathVariable("showId") String id) throws Exception {

		log.info("getRemainSeatsCnt - showId : " + id);

		return ok(GetRemainSeatsCntResDto.builder()
			.remainSeats(
				List.of(
					GetRemainSeatsCntResDto.RemainSeat.builder()
						.id("1")
						.grade("S")
						.cnt(40)
						.build(),
					GetRemainSeatsCntResDto.RemainSeat.builder()
						.id("1")
						.grade("A")
						.cnt(30)
						.build()

				)

			)
			.build());
	}

	/**
	 *공연 등록
	 * @param registerShowReqDto
	 * @return
	 */
	@PostMapping("")
	public BaseResponse registerShow(@RequestBody RegisterShowReqDto registerShowReqDto) {

		log.debug("registerShow - registproductRequest : " + registerShowReqDto);

		return ok("등록 성공");

	}

}
