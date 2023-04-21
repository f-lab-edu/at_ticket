package com.atticket.product.controller;

import static com.atticket.common.response.BaseResponse.ok;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
import com.atticket.common.response.SampleDto;
import com.atticket.commonEnum.AgeLimit;
import com.atticket.commonEnum.Category;
import com.atticket.commonEnum.Region;
import com.atticket.commonEnum.SubCategory;
import com.atticket.product.dto.response.GetProductResDto;
import com.atticket.show.dto.response.GetShowsResDto;
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
	public BaseResponse<GetProductResDto> getProduct(@PathVariable("productId") String id) throws
		Exception {

		log.debug("getProductDetail - productId : " + id);

		if (id.equals("error")) {
			throw new Exception("unexpected error our");
		}
		if (id.equals("test-error")) {
			throw new BaseException(BaseStatus.TEST_ERROR);
		}

		return ok(GetProductResDto.builder()
			.region(Region.SEOUL)
			.product(
				GetProductResDto.Product.builder()
					//.category(Category.Musical)
					.category(Category.findBySubCategory(SubCategory.ORIGINAL))
					.subCategory(SubCategory.ORIGINAL)
					.name("캣츠")
					.explain("설명")
					.runningTime(LocalTime.of(2, 00))
					.startDate(LocalDate.of(2023, 04, 21))
					.endDate(LocalDate.of(2024, 04, 21))
					.ageLimit(AgeLimit.FIFTEEN)
					.image("http://이미지.jpg")
					.build()
			)

			.seatGrades(List.of(
					GetProductResDto.Grade.builder()
						.type("A")
						.price("5000")
						.build(),
					GetProductResDto.Grade.builder()
						.type("B")
						.price("10000")
						.build()
				)
			)
			.showDates(
				List.of(
					LocalDate.of(2023, 03, 01),
					LocalDate.of(2023, 03, 02),
					LocalDate.of(2023, 04, 01)
				)
			)

			.build());
	}

	//일자별 공연 조회
	@GetMapping("/{productId}/shows")
	public BaseResponse<GetShowsResDto> getShows(@PathVariable("productId") String id,
		@RequestParam("date") String date) throws Exception {

		log.debug("getShowList - productId : " + id);
		log.debug("getShowList - date : " + date);

		return ok(
			GetShowsResDto.builder()
				.session(
					List.of(
						GetShowsResDto.Session.builder()
							.id("1")
							.time(LocalDateTime.of(2023, 01, 12, 10, 00))
							.build(),
						GetShowsResDto.Session.builder()
							.id("2")
							.time(LocalDateTime.of(2023, 01, 13, 10, 00))
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
