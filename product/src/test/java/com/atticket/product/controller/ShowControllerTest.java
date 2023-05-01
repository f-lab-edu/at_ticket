package com.atticket.product.controller;

import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.atticket.product.domain.Grade;
import com.atticket.product.domain.ReservedSeat;
import com.atticket.product.domain.ShowSeat;
import com.atticket.product.repository.GradeRepository;
import com.atticket.product.repository.ReservedSeatRepository;
import com.atticket.product.repository.ShowSeatRepository;

@WebMvcTest(ShowController.class)
public class ShowControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	ShowSeatRepository showSeatRepository;

	@MockBean
	GradeRepository gradeRepository;

	@MockBean
	ReservedSeatRepository reservedSeatRepository;

	@Test
	@DisplayName("공연의 남은 좌석수 조회 테스트")
	void getShowsTest() throws Exception {

		//given
		//자리 - 등급 매핑 정보
		given(showSeatRepository.findShowSeatByShowId(1L)).willReturn(
			Arrays.asList(
				ShowSeat.builder()
					.id(1L)
					.showId(1L)
					.seatList("1,2,3,4,5,6,7")
					.gradeId(1L)
					.productId(1L)
					.build(),
				ShowSeat.builder()
					.id(2L)
					.showId(1L)
					.seatList("8,9,10,11")
					.gradeId(2L)
					.productId(1L)
					.build()
			)
		);

		//등급  정보
		given(gradeRepository.findById(1L)).willReturn(
			Optional.ofNullable(Grade.builder()
				.id(1L)
				.type("A")
				.price(5000)
				.productId(1L)
				.build())
		);

		given(gradeRepository.findById(2L)).willReturn(
			Optional.ofNullable(Grade.builder()
				.id(2L)
				.type("B")
				.price(1000)
				.productId(1L)
				.build())
		);

		//예약 좌석 정보
		given(reservedSeatRepository.findShowSeatByShowId(1L)).willReturn(
			Arrays.asList(
				ReservedSeat.builder()
					.id(1L)
					.showId(1L)
					.seatId(1L)
					.build(),
				ReservedSeat.builder()
					.id(2L)
					.showId(1L)
					.seatId(2L)
					.build(),
				ReservedSeat.builder()
					.id(3L)
					.showId(1L)
					.seatId(8L)
					.build()
			)
		);

		Long showId = 1L;

		mockMvc.perform(
				get("/shows/" + showId + "/seats/count")
			)
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.data.remainSeats", notNullValue()))
			.andDo(MockMvcResultHandlers.print());

	}
}
