package com.atticket.product.controller;

import static com.atticket.common.response.BaseResponse.ok;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atticket.common.response.BaseResponse;
import com.atticket.product.domain.Grade;
import com.atticket.product.domain.SeatGrade;
import com.atticket.product.dto.request.RegisterShowReqDto;
import com.atticket.product.dto.response.GetRemainSeatsCntResDto;
import com.atticket.product.dto.response.GetRemainSeatsResDto;
import com.atticket.product.service.ProductService;
import com.atticket.product.service.ShowService;

import lombok.extern.slf4j.Slf4j;

//좌석 조회
@Slf4j
@RestController
@RequestMapping("/shows")
public class ShowController {

	@Autowired
	ShowService showService;

	@Autowired
	ProductService productService;

	//공연의 남은 좌석 조회
	@GetMapping("/{showId}/seats")
	public BaseResponse<GetRemainSeatsResDto> getRemainSeats(@PathVariable("showId") String id) {

		return ok(GetRemainSeatsResDto.builder().showSeats(List.of(
			GetRemainSeatsResDto.ShowSeat.builder()

				.id(1L)
				.space("1층")
				.locX("12")
				.locY("22")
				.row("T열")
				.rowNum(1)
				.grade("VIP")
				.price(120000)
				.build(),
			GetRemainSeatsResDto.ShowSeat.builder()
				.id(2L)
				.space("1층")
				.locX("15")
				.locY("25")
				.row("T열")
				.rowNum(2)
				.grade("VIP")
				.price(120000)
				.build(),
			GetRemainSeatsResDto.ShowSeat.builder()
				.id(3L)
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
	public BaseResponse<GetRemainSeatsCntResDto> getRemainSeatsCnt(@PathVariable("showId") Long showId) {

		log.debug("getRemainSeatsCnt - showId : " + showId);

		//공연의 좌석 - 등급 매핑 정보 조회
		List<SeatGrade> seatGrades = showService.getSeatGradeByShowId(showId);

		List<GetRemainSeatsCntResDto.RemainSeat> remainSeats = new ArrayList<>();

		//showId에 해당하는 좌석 없음.
		if (seatGrades.size() == 0) {

			return ok();

		} else {

			//productId로 Grade List 조회
			Long productId = seatGrades.get(0).getProductId();
			List<Grade> grades = productService.getGradesById(productId);

			//Grade List의 Grade로 좌석 카운트하기
			for (Grade grade : grades) {

				GetRemainSeatsCntResDto.RemainSeat remainSeat
					= GetRemainSeatsCntResDto.RemainSeat.builder()
					.showId(showId)
					.grade(grade.getType())
					.cnt((int)seatGrades.stream().filter(s -> s.getGradeId().equals(grade.getId())).count())
					.build();

				remainSeats.add(remainSeat);
			}
		}

		return ok(
			GetRemainSeatsCntResDto.builder()
				.remainSeats(remainSeats)
				.build()
		);
	}

	//공연 등록
	@PostMapping("")
	public BaseResponse registerShow(@RequestBody RegisterShowReqDto registerShowReqDto) {
		log.info("registerShow - registerShowRequest : " + registerShowReqDto);
		return ok();
	}

}
