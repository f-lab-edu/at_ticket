package com.atticket.product.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.atticket.product.domain.WishProduct;
import com.atticket.product.kafka.producer.ProductProducer;
import com.atticket.product.repository.WishProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WishProductService {

	private final WishProductRepository wishProductRepository;
	private final ProductProducer productProducer;

	/**
	 * 관심 상품을 등록한 유저들을 조회하고, Mail알림 이벤트를 이벤트를 발행할 producer를 호출한다
	 * @param productId
	 * @param showData
	 */
	public void sendNotifyMail(Long productId, List<String> showData) {

		//관심 상품 등록해논 유저 정보 조회
		List<WishProduct> wishProduct = wishProductRepository.findByProductId(productId);

		if (wishProduct.size() > 0) {
			productProducer.sendNotifyMail(com.atticket.common.kafka.payload.WishProduct.builder()
				.productId(productId)
				.productNm(wishProduct.get(0).getName())
				.showData(showData)
				.userData(
					wishProduct.stream().map(x -> com.atticket.common.kafka.payload.WishProduct.UserData.builder()
						.id(x.getUserId())
						.email(x.getUserEmail()).build()).collect(Collectors.toList())
				)
				.build());
		}

	}
}
