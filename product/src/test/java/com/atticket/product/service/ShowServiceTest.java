package com.atticket.product.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.atticket.product.domain.Show;
import com.atticket.product.repository.HallRepository;
import com.atticket.product.repository.ProductRepository;
import com.atticket.product.repository.ShowRepository;

class ShowServiceTest {

	// service
	private ShowService showService;

	// repository
	private ShowRepository showRepository = mock(ShowRepository.class);
	private ProductRepository productRepository = mock(ProductRepository.class);
	private HallRepository hallRepository = mock(HallRepository.class);

	@BeforeEach
	public void setUpTest() {
		showService = new ShowService(showRepository, productRepository, hallRepository);

	}

	@Test
	@DisplayName("상품의 공연 정보 리스트 조회")
	void getShowsByProductId() {
		//Given
		Long productId = 1L;
		// List<Show> givenShows = Arrays.asList();

		List<Show> givenShows;
		givenShows = null;
		when(showRepository.findByProduct_id(productId)).thenReturn(givenShows);

		//When
		List<Show> results = showService.getShowsByProductId(productId);

		//Then
		Assertions.assertEquals(givenShows, results);
		verify(showRepository).findByProduct_id(1L);

	}

}
