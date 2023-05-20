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
import com.atticket.product.dto.response.RegisterShowResDto;
import com.atticket.product.dto.service.GetRemainSeatCntSvcDto;
import com.atticket.product.dto.service.RegisterShowServiceDto;
import com.atticket.product.service.ShowSeatService;
import com.atticket.product.service.ShowService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//좌석 조회
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/shows")
public class ShowController {

	private final ShowSeatService showSeatService;
	private final ShowService showService;

	//공연의 남은 좌석 조회
	@GetMapping("/{showId}/seats")
	public BaseResponse<GetRemainSeatsResDto> getRemainSeats(@PathVariable("showId") Long id) {

		return ok(GetRemainSeatsResDto.construct(showSeatService.getRemainSeatsByShowId(id)));
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
	public BaseResponse<RegisterShowResDto> registerShow(@RequestBody RegisterShowReqDto registerShowReqDto,
		@PathVariable("productId") Long productId) {

		log.debug("registerShow - registerShowReqDto : " + registerShowReqDto);
		log.debug("registerShow - productId : " + productId);

		List<Long> showIds = registerShowReqDto.getShows()
			.stream()
			.map(showInfo -> showService.registerShow(productId,
				RegisterShowServiceDto.builder()
					.date(showInfo.getDate())
					.time(showInfo.getTime())
					.session(showInfo.getSession())
					.hallId(showInfo.getHallId())
					.seats(showInfo.getSeats()
						.stream()
						.map(seatInfo -> RegisterShowServiceDto.SeatInfo.builder()
							.ids(seatInfo.getIds())
							.grade(seatInfo.getGrade())
							.build())
						.collect(Collectors.toList()))
					.build()
			))
			.collect(Collectors.toList());

		//등록 공연 id 리턴
		return ok(
			RegisterShowResDto.builder()
				.showIds(showIds)
				.build()
		);
	}

}
