package com.atticket.product.controller;

import static com.atticket.common.response.BaseResponse.ok;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.atticket.common.response.BaseException;
import com.atticket.common.response.BaseResponse;
import com.atticket.common.response.BaseStatus;
import com.atticket.product.dto.request.GetProductsReqDto;
import com.atticket.product.dto.response.GetProductDetailResDto;
import com.atticket.product.dto.response.GetProductsResDto;
import com.atticket.product.dto.response.GetShowListResDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/products")
public class ProductController {

	/**
	 * 상품 검색
	 */
	@GetMapping("")
	public BaseResponse<GetProductsResDto> getProducts(@ModelAttribute GetProductsReqDto req) {

		log.info("searchProductList - request : " + req);

		return ok(GetProductsResDto.builder().productList(List.of(
			GetProductsResDto.Product.builder()
				.image("https://s3.atticket.com/products/images/cats")
				.id("product-1")
				.name("뮤지컬 〈캣츠〉 오리지널 내한－성남（Musical CATS）")
				.place(GetProductsResDto.Place.builder()
					.id("place-1")
					.name("성남아트센터")
					.build()
				)
				.startDate("20230505")
				.endDate("20230507")
				.runningTime("160분(인터미션:20분)")
				.ageLimit("8세이상 관람가능")
				.category("뮤지컬")
				.build(),
			GetProductsResDto.Product.builder()
				.image("https://s3.atticket.com/products/images/cats")
				.id("product-2")
				.name("뮤지컬 〈캣츠〉 오리지널 내한－대전（Musical CATS）")
				.place(GetProductsResDto.Place.builder()
					.id("place-2")
					.name("대전예술의전당")
					.build()
				)
				.startDate("20230519")
				.endDate("20230521")
				.runningTime("160분(인터미션:20분)")
				.ageLimit("8세이상 관람가능")
				.category("뮤지컬")
				.build(),
			GetProductsResDto.Product.builder()
				.image("https://s3.atticket.com/products/images/cats")
				.id("product-3")
				.name("뮤지컬 〈캣츠〉 오리지널 내한－수원（Musical CATS）")
				.place(GetProductsResDto.Place.builder()
					.id("place-3")
					.name("경기아트센터")
					.build()
				)
				.startDate("20230512")
				.endDate("20230514")
				.runningTime("160분(인터미션:20분)")
				.ageLimit("8세이상 관람가능")
				.category("뮤지컬")
				.build())
		).build());
	}

	/**
	 * 상품 정보 조회
	 */
	@GetMapping("/{productId}")
	public BaseResponse<GetProductDetailResDto> getProductDetail(@PathVariable("productId") String id) throws
		Exception {

		log.info("getProductDetail - productId : " + id);

		if (id.equals("error")) {
			throw new Exception("unexpected error our");
		}
		if (id.equals("test-error")) {
			throw new BaseException(BaseStatus.TEST_ERROR);
		}

		return ok(GetProductDetailResDto.builder()
			.productName("캣츠")
			.productContents(
				//공연 상세 정보
				GetProductDetailResDto.ProductContents.builder()
					.productExplain("캣츠 설명")
					.productRunningTime("100")
					.productPeriodStr("20230416")
					.productPeriodEnd("20240416")
					.build()
			)
			.productPlace(
				GetProductDetailResDto.ProductPlace.builder()
					.placeAddress("서울 어쩌구")
					.placeNumber("0101231234")
					.build()
			)
			.seatTypesList(List.of(
				GetProductDetailResDto.SeatType.builder()
					.seatGrade("A")
					.seatPrice("5000")
					.build(),
				GetProductDetailResDto.SeatType.builder()
					.seatGrade("B")
					.seatPrice("10000")
					.build()
				)
			)
			.showDateList(
				List.of(
					"20230416",
					"20230417",
					"20230418"
				)
			)

			.build());
	}

	/**
	 * 일자별 공연 조회
	 */
	@GetMapping("/{productId}/shows")
	public BaseResponse<GetShowListResDto> getShowList(@PathVariable("productId") String id,
		@RequestParam("date") String date) {

		log.info("getShowList - productId : " + id);
		log.info("getShowList - date : " + date);

		return ok(
			GetShowListResDto.builder()
				.showSeq(
					List.of(
						GetShowListResDto.ShowSeq.builder()
							.showId("1")
							.showTime("1000")
							.build(),
						GetShowListResDto.ShowSeq.builder()
							.showId("2")
							.showTime("1300")
							.build()
					)
				).build()
		);
	}

	/**
	 * 상품 내용 삭제
	 * */
	@DeleteMapping("/{productId}")
	public BaseResponse deleteProductContent(@PathVariable("productId") String id) {
		return ok();
	}

}
