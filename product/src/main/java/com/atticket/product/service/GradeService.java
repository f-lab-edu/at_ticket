package com.atticket.product.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.atticket.product.domain.Grade;
import com.atticket.product.repository.GradeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GradeService {

	private final GradeRepository gradeRepository;

	/**
	 * productId로 grade 정보 리스트 조회
	 * @param productId
	 * @return
	 */
	public List<Grade> getGradesByProductId(Long productId) {
		return gradeRepository.findByProduct_id(productId);
	}

	public Grade getGradeById(Long id) {
		return gradeRepository.findById(id).orElse(null);
	}
}
