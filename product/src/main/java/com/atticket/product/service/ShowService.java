package com.atticket.product.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.atticket.common.response.BaseException;
import com.atticket.common.response.BaseStatus;
import com.atticket.product.domain.Hall;
import com.atticket.product.domain.Product;
import com.atticket.product.domain.Show;
import com.atticket.product.repository.ShowRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShowService {

	// service
	private final HallService hallService;
	private final ProductService productService;

	// repository
	private final ShowRepository showRepository;

	public Show getShowById(Long id) {
		return showRepository.findById(id).orElse(null);
	}

	/**
	 * 상품의 공연 정보 리스트 조회
	 * @param productId
	 * @return
	 */
	public List<Show> getShowsByProductId(Long productId) {
		return showRepository.findByProduct_id(productId);
	}

	/**
	 * 상품의 공연 날짜 리스트 조회
	 * @param productId
	 * @return
	 */
	public List<LocalDate> getShowDatesByProductId(Long productId) {
		return showRepository.findByProduct_id(productId)
			.stream()
			.map(Show::getDate)
			.collect(Collectors.toList());
	}

	/**
	 * 특정 날짜의 공연 리스트 조회
	 * @param productId
	 * @param date
	 * @return
	 */
	public List<Show> getShowDateByProductId(Long productId, LocalDate date) {
		return showRepository.findByProduct_id(productId)
			.stream()
			.filter(show -> show.getDate().equals(date))
			.collect(Collectors.toList());
	}

	public Long saveShow(Long productId, LocalDate date, LocalTime time, Long hallId, int session) {

		Product product = productService.getProductById(productId);
		if (Objects.isNull(product)) {
			throw new BaseException(BaseStatus.INVALID_PRODUCT);
		}

		Hall hall = hallService.getHallById(hallId);
		if (Objects.isNull(hall)) {
			throw new BaseException(BaseStatus.INVALID_HALL);
		}

		if (!product.getPlace().equals(hall.getPlace())) {
			throw new BaseException(BaseStatus.PRODUCT_PLACE_NOT_SAME_HALL_PLACE);
		}

		Show show = Show.builder()
			.product(product)
			.date(date)
			.time(time)
			.hall(hall)
			.session(session)
			.build();

		//공연 저장

		return showRepository.save(show).getId();
	}
}
