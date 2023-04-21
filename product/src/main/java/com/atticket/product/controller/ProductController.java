package com.atticket.product.controller;

import static com.atticket.common.response.BaseResponse.ok;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/products")
public class ProductController {

	/**
	 * 상품 검색
	 */
	@GetMapping("")
	public BaseResponse<SampleDto> getProducts() {
		return ok(SampleDto.builder().content("hi").build());
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

	//상품 내용 삭제
	@DeleteMapping("/{productId}")
	public BaseResponse deleteProduct(@PathVariable("productId") String id) {

		return ok("삭제 완료");

	}

}
