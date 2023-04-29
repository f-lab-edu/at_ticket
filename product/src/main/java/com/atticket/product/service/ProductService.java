package com.atticket.product.service;

import org.springframework.stereotype.Service;

import com.atticket.common.response.BaseException;
import com.atticket.common.response.BaseStatus;
import com.atticket.product.repository.GradeRepository;
import com.atticket.product.repository.ProductRepository;
import com.atticket.product.repository.ShowRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;
	private final ShowRepository showRepository;
	private final GradeRepository gradeRepository;

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

}
