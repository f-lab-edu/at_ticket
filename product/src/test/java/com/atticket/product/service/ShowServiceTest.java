package com.atticket.product.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.atticket.product.domain.Show;
import com.atticket.product.repository.ShowRepository;

class ShowServiceTest {

	private ShowRepository showRepository = mock(ShowRepository.class);
	private ShowService showService;

	@BeforeEach
	public void setUpTest() {
		showService = new ShowService(showRepository);
	}

	@Test
	@DisplayName("상품의 공연 정보 리스트 조회")
	void getShowsByProductId() {
		//Given
		Long productId = 1L;

		List<Show> givenShows = Arrays.asList(
			Show.builder()
				.id(1L)
				.time(LocalTime.of(10, 0, 0))
				.session(1)
				.date(LocalDate.of(2023, 3, 1))
				.productId(1L)
				.build(),
			Show.builder()
				.id(2L)
				.time(LocalTime.of(12, 0, 0))
				.session(2)
				.date(LocalDate.of(2023, 3, 1))
				.productId(1L)
				.build()
		);

		when(showRepository.findShowsByProductId(productId)).thenReturn(givenShows);

		//when
		List<Show> results = showService.getShowsByProductId(productId);

		for (int i = 0; i < results.size(); i++) {
			Assertions.assertEquals(givenShows.get(i).getId(), results.get(i).getId());
			Assertions.assertEquals(givenShows.get(i).getTime(), results.get(i).getTime());
			Assertions.assertEquals(givenShows.get(i).getSession(), results.get(i).getSession());
			Assertions.assertEquals(givenShows.get(i).getDate(), results.get(i).getDate());
			Assertions.assertEquals(givenShows.get(i).getProductId(), results.get(i).getProductId());
		}

		verify(showRepository).findShowsByProductId(1L);

	}

}
