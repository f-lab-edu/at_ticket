package com.atticket.product.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.atticket.product.service.ShowSeatService;
import com.atticket.product.service.ShowService;

@WebMvcTest(ShowController.class)
public class ShowControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	ShowSeatService showSeatService;

	@MockBean
	ShowService showService;

	@Test
	@DisplayName("공연의 남은 좌석수 조회 테스트")
	void getShowsTest() throws Exception {

		//given
		//자리 - 등급 매핑 정보
		given(showSeatService.getRemainSeatCntByShowId(1L)).willReturn(
			Arrays.asList(
				GetRemainSeatCntSvcDto.builder()
					.showId(1L)
					.gradeId(1L)
					.gradeNm("A")
					.seatCnt(5)
					.build(),
				GetRemainSeatCntSvcDto.builder()
					.showId(1L)
					.gradeId(2L)
					.gradeNm("B")
					.seatCnt(3)
					.build()
			)
		);

		Long showId = 1L;

		mockMvc.perform(get("/shows/" + showId + "/seats/count"))
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.data.remainSeats", notNullValue()))
			.andDo(MockMvcResultHandlers.print());

		verify(showSeatService).getRemainSeatCntByShowId(1L);

	}
}
