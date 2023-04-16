package com.atticket.product.controller;

import static com.atticket.common.response.BaseResponse.ok;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atticket.common.response.BaseResponse;
import com.atticket.product.dto.request.RegistProductReqDto;
import com.atticket.product.dto.response.GetRemainSeatsCntResDto;

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
	public BaseResponse registProduct(@RequestBody RegistProductReqDto registproductRequest) {

		log.info("registProduct - registproductRequest : " + registproductRequest);

		return ok("등록 성공");

	}

}
