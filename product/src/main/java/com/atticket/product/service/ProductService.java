package com.atticket.product.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.atticket.product.domain.Grade;
import com.atticket.product.domain.Product;
import com.atticket.product.domain.Show;
import com.atticket.product.repository.GradeRepository;
import com.atticket.product.repository.ProductRepository;
import com.atticket.product.repository.SeatRepository;
import com.atticket.product.repository.ShowRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;
	private final ShowRepository showRepository;
	private final GradeRepository gradeRepository;
	private final SeatRepository seatRepository;

	/**
	 *상품id로 상품 조회
	 * @param productId
	 * @return
	 */
	public Product getProductByProductId(Long productId) {

		return productRepository.findById(productId).get();

	}

	/**
	 *상품id로 공연 정보 조회
	 * @param productId
	 * @return
	 */
	public List<Show> getShowsByProductId(Long productId) {

		return showRepository.findShowsByProductId(productId);
	}

	/**
	 * 상품id로 등급 정보 조회
	 * @param productId
	 * @return
	 */
	public List<Grade> getGradesByProductId(Long productId) {
		return gradeRepository.findGradeByProductId(productId);
	}
}
