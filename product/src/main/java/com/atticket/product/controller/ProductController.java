package com.atticket.product.controller;

import static com.atticket.common.response.BaseResponse.ok;

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
import com.atticket.commonenum.AgeLimit;
import com.atticket.commonenum.Category;
import com.atticket.commonenum.Region;
import com.atticket.commonenum.SubCategory;
import com.atticket.product.dto.request.GetProductsReqDto;
import com.atticket.product.dto.response.GetProductResDto;
import com.atticket.product.dto.response.GetProductsResDto;
import com.atticket.show.dto.response.GetShowsResDto;

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
				.startDate(LocalDate.of(2023, 05, 05))
				.endDate(LocalDate.of(2023, 05, 07))
				.runningTime(LocalTime.of(2, 40))
				.interMission(LocalTime.of(0, 20))
				.ageLimit(AgeLimit.EIGHT)
				.category(Category.Musical)
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
				.startDate(LocalDate.of(2023, 05, 19))
				.endDate(LocalDate.of(2023, 05, 21))
				.runningTime(LocalTime.of(2, 40))
				.interMission(LocalTime.of(0, 20))
				.ageLimit(AgeLimit.EIGHT)
				.category(Category.Musical)
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
				.startDate(LocalDate.of(2023, 05, 12))
				.endDate(LocalDate.of(2023, 05, 14))
				.runningTime(LocalTime.of(2, 40))
				.interMission(LocalTime.of(0, 20))
				.ageLimit(AgeLimit.EIGHT)
				.category(Category.Musical)
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
