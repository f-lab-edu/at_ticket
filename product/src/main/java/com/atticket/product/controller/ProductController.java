package com.atticket.product.controller;

import static com.atticket.common.response.BaseResponse.ok;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atticket.common.response.BaseException;
import com.atticket.common.response.BaseResponse;
import com.atticket.common.response.BaseStatus;
import com.atticket.common.response.SampleDto;

@RestController
@RequestMapping("/products")
public class ProductController {

	/**
	 * 상품 검색
	 * */
	@GetMapping("")
	public BaseResponse<SampleDto> getProducts() {
		return ok(SampleDto.builder().content("hi").build());
	}

	/**
	 * 상품 정보 조회
	 */
	@GetMapping("/{id}")
	public BaseResponse<SampleDto> getTicketInfo(@PathVariable("id") String id) throws Exception {
		if (id.equals("error")) {
			throw new Exception("unexpected error our");
		}
		if (id.equals("test-error")) {
			throw new BaseException(BaseStatus.TEST_ERROR);
		}
		return ok(SampleDto.builder().content(id).build());
	}
}
