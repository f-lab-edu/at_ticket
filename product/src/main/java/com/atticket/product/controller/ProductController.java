package com.atticket.product.controller;

import static com.atticket.common.response.BaseResponse.*;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atticket.common.response.BaseResponse;
import com.atticket.common.response.SampleDto;
import com.atticket.product.dto.response.ProductDetailResDto;
import com.atticket.product.dto.response.SeatsSearchResDto;

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

	//
	// /**
	//  * 상품 정보 조회
	//  */
	// @GetMapping("/{id}")
	// public BaseResponse<SampleDto> getTicketInfo(@PathVariable("id") String id) throws Exception {
	// 	if (id.equals("error")) {
	// 		throw new Exception("unexpected error our");
	// 	}
	// 	if (id.equals("test-error")) {
	// 		throw new BaseException(BaseStatus.TEST_ERROR);
	// 	}
	// 	return ok(SampleDto.builder().content(id).build());
	// }

	//상품 상세 조회
	@GetMapping("/{productId}")
	public BaseResponse<ProductDetailResDto> getProductDetail(@PathVariable String productId) {

		//공연 내용
		ProductDetailResDto.Contents contents = ProductDetailResDto.Contents.builder()
			.showExplain("공연설명")
			.showTime("1시간")
			.build();

		//공연 날짜/회차별 좌석 정보
		SeatsSearchResDto seatInfo = SeatsSearchResDto.builder()
			.session("1회차")
			.remainSeatList(List.of(
					SeatsSearchResDto.RemainSeat.builder()
						.type("A")
						.count(10)
						.price(1000)
						.build(),
					SeatsSearchResDto.RemainSeat.builder()
						.type("B")
						.count(8)
						.price(5000)
						.build()
				)
			)
			.build();

		return ok(
			ProductDetailResDto.builder()
				.name("캣츠")
				.contents(contents)
				.seatInfo(seatInfo)
				.build()

		);

	}
	//
	// //상품 등록
	// @PostMapping("")
	// public com.atticket.dto.ResultResponseDto registProduct(
	// 	@RequestBody ProductRegisterReqDto registproductRequest) {
	//
	// 	return com.atticket.dto.ResultResponseDto.builder()
	// 		.code(200)
	// 		.message("등록 완료")
	// 		.build();
	// }
	//
	// //상품 내용 삭제
	// @DeleteMapping("/contents/{productContentsId}")
	// public com.atticket.dto.ResultResponseDto deleteProductContenet(@PathVariable String productContentsId) {
	//
	// 	return com.atticket.dto.ResultResponseDto.builder()
	// 		.code(200)
	// 		.message("삭제 완료")
	// 		.build();
	// }

}
