package com.atticket.product.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.atticket.common.response.BaseException;
import com.atticket.common.response.BaseStatus;
import com.atticket.product.domain.Hall;
import com.atticket.product.domain.Product;
import com.atticket.product.domain.Show;
import com.atticket.product.repository.HallRepository;
import com.atticket.product.repository.ProductRepository;
import com.atticket.product.repository.ShowRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShowService {

	// repository
	private final ShowRepository showRepository;
	private final ProductRepository productRepository;
	private final HallRepository hallRepository;

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

		Optional<Product> product = productRepository.findById(productId);
		if (product.isEmpty()) {
			throw new BaseException(BaseStatus.INVALID_PRODUCT);
		}

		Optional<Hall> hall = hallRepository.findById(hallId);
		if (hall.isEmpty()) {
			throw new BaseException(BaseStatus.INVALID_HALL);
		}

		if (product.get().getPlace() == null || hall.get().getPlace() == null) {
			throw new BaseException(BaseStatus.REQUIRED_PLACE);
		} else {
			if (!product.get().getPlace().equals(hall.get().getPlace())) {
				throw new BaseException(BaseStatus.PRODUCT_PLACE_NOT_SAME_HALL_PLACE);
			}
		}

		//같은 날짜의 같은 시간에 등록하려는 시간이 이미 등록되어 있다면  exception
		List<Show> existShowTimeList = getShowDateByProductId(productId, date).stream()
			.filter(show -> show.getTime().equals(time))
			.collect(Collectors.toList());
		if (existShowTimeList.size() > 0) {
			throw new BaseException(BaseStatus.EXIST_SHOW_TIME);
		}

		Show show = Show.builder()
			.product(product.get())
			.date(date)
			.time(time)
			.hall(hall.get())
			.session(session)
			.build();

		//공연 저장
		return showRepository.save(show).getId();
	}

	public int deleteByProduct(Product product) {
		return showRepository.deleteByProduct(product);
	}
}
