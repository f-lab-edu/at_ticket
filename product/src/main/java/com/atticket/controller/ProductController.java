package com.atticket.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atticket.common.BaseException;
import com.atticket.common.BaseResponse;
import com.atticket.common.BaseStatus;
import com.atticket.common.SampleDto;

@RestController
@RequestMapping("/products")
public class ProductController {

	/**
	 * 상품 검색
	 * */
	@GetMapping("")
	public BaseResponse<SampleDto> getProducts() {
		return new BaseResponse<>(SampleDto.builder().content("hi").build());
	}

	/**
	 * 상품 정보 조회
	 */
	@GetMapping("/{id}")
	public BaseResponse<SampleDto> getTicketInfo(@PathVariable("id") String id) throws Exception {
		if (id.equals("error")) {
			throw new Exception("unexpected error occur");
		}
		if (id.equals("test-error")) {
			throw new BaseException(BaseStatus.TEST_ERROR);
		}
		return new BaseResponse<>(SampleDto.builder().content(id).build());
	}
}
