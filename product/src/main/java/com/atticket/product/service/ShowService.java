package com.atticket.product.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.atticket.product.domain.Show;
import com.atticket.product.repository.ShowRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShowService {

	private final ShowRepository showRepository;

	/**
	 * 상품의 공연 정보 리스트 조회
	 * @param productId
	 * @return
	 */
	public List<Show> getShowsByProductId(Long productId) {
		return showRepository.findShowsByProductId(productId);
	}

	/**
	 * 상품의 공연 날짜 리스트 조회
	 * @param productId
	 * @return
	 */
	public List<LocalDate> getShowDatesByProductId(Long productId) {
		return showRepository.findShowsByProductId(productId)
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
		return showRepository.findShowsByProductId(productId)
			.stream()
			.filter(show -> show.getDate().equals(date))
			.collect(Collectors.toList());
	}

}
