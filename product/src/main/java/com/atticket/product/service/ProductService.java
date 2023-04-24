package com.atticket.product.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.atticket.common.response.BaseException;
import com.atticket.common.response.BaseStatus;
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
	 * 상품 삭제
	 * @param productId
	 */
	public void deleteProduct(Long productId) {

		//Todo 해당 상품Id를 가지고 있는 공연 정보들 우선 삭제 필요

		//해당 id 상품이 없으면 Exception
		if (productRepository.findById(productId).isEmpty()) {
			throw new BaseException(BaseStatus.TEST_ERROR);
		}

		productRepository.deleteById(productId);
	}

	/**
	 *상품id로 상품 조회
	 * @param productId
	 * @return
	 */
	public Product getProductById(Long productId) {

		return productRepository.findById(productId).get();

	}

	/**
	 *상품id로 공연 정보 조회
	 * @param productId
	 * @return
	 */
	public List<Show> getShowsById(Long productId) {

		return showRepository.findShowsByProductId(productId);
	}

	/**
	 * 상품id로 등급 정보 조회
	 * @param productId
	 * @return
	 */
	public List<Grade> getGradesById(Long productId) {
		return gradeRepository.findGradeByProductId(productId);
	}

}
