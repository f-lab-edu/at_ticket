package com.atticket.product.controller;

import static com.atticket.common.response.BaseResponse.*;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atticket.common.response.BaseResponse;
import com.atticket.product.dto.request.GetSeatsInfoReqDto;
import com.atticket.product.dto.request.RegisterShowReqDto;
import com.atticket.product.dto.response.GetRemainSeatsCntResDto;
import com.atticket.product.dto.response.GetShowSeatsResDto;
import com.atticket.product.dto.response.RegisterShowResDto;
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

	//공연의 전체 좌석 조회
	@GetMapping("/{showId}/seats")
	public BaseResponse<GetShowSeatsResDto> getShowSeats(@PathVariable("showId") Long id) {

		return ok(GetShowSeatsResDto.construct(showSeatService.getShowSeats(id)));
	}

	/**
	 * 공연의 남은 좌석수 조회
	 * @param showId
	 * @return
	 */
	@GetMapping("/{showId}/seats/count")
	public BaseResponse<GetRemainSeatsCntResDto> getRemainSeatsCnt(@PathVariable("showId") Long showId) {

		log.debug("getRemainSeatsCnt - showId : " + showId);

		//등급별 남은 좌석 조회
		List<GetRemainSeatCntSvcDto> remainSeatCnts = showSeatService.getRemainSeatCntByShowId(showId);

		return ok(
			GetRemainSeatsCntResDto.construct(remainSeatCnts)
		);
	}

	/**
	 * 공연 등록
	 * @param registerShowReqDto
	 * @param productId
	 * @return
	 */
	@PostMapping("/product/{productId}")
	public BaseResponse<RegisterShowResDto> registerShow(@Valid @RequestBody RegisterShowReqDto registerShowReqDto,
		@PathVariable("productId") Long productId) {

		System.out.println(registerShowReqDto.getShows().get(0).getSession());

		log.debug("registerShow - registerShowReqDto : " + registerShowReqDto);
		log.debug("registerShow - productId : " + productId);

		//등록 공연 id 리턴
		return ok(RegisterShowResDto.builder()
			.showIds(showSeatService.registerShow(productId, registerShowReqDto.convert()))
			.build());
	}

	// 공연 좌석 가격 조회
	@PostMapping("/{showId}/seats/price")
	public BaseResponse<Integer> getSeatsPrice(@PathVariable("showId") Long id,
		@Valid @RequestBody GetSeatsInfoReqDto reqDto) {

		return ok(showSeatService.getSeatsPrice(id, reqDto.getSeatIds()));
	}
}
