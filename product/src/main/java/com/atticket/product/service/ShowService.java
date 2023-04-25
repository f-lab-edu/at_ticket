package com.atticket.product.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.atticket.product.domain.SeatGrade;
import com.atticket.product.repository.SeatGradeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShowService {

	private final SeatGradeRepository seatGradeRepository;

	/**
	 *공연의 좌석 - 등급 매핑 정보 조회
	 * @param showId
	 * @return
	 */
	public List<SeatGrade> getSeatGradeByShowId(Long showId) {

		return seatGradeRepository.findByshowId(showId);
	}

}
