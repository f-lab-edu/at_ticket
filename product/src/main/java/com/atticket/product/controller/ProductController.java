package com.atticket.product.controller;

import static com.atticket.common.response.BaseResponse.ok;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
import com.atticket.product.domain.Show;
import com.atticket.product.dto.request.GetProductsReqDto;
import com.atticket.product.dto.response.GetProductResDto;
import com.atticket.product.dto.response.GetProductsResDto;
import com.atticket.product.dto.response.GetShowsResDto;
import com.atticket.product.service.GradeService;
import com.atticket.product.service.ProductService;
import com.atticket.product.service.ShowService;
import com.atticket.product.type.AgeLimit;
import com.atticket.product.type.Category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

	private final ProductService productService;
	private final ShowService showService;
	private final GradeService gradeService;

	/**
	 * 상품 검색
	 */
	@GetMapping("")
	public BaseResponse<GetProductsResDto> getProducts(@ModelAttribute GetProductsReqDto req) {

		log.info("searchProductList - request : " + req);

		return ok(GetProductsResDto.builder().productList(List.of(
			GetProductsResDto.Product.builder()
				.image("https://s3.atticket.com/products/images/cats")
				.id(1L)
				.name("뮤지컬 〈캣츠〉 오리지널 내한－성남（Musical CATS）")
				.place(GetProductsResDto.Place.builder()
					.id(1L)
					.name("성남아트센터")
					.build()
				)
				.startDate(LocalDate.of(2023, 05, 05))
				.endDate(LocalDate.of(2023, 05, 07))
				.runningTime(LocalTime.of(2, 40))
				.interMission(LocalTime.of(0, 20))
				.ageLimit(AgeLimit.EIGHT)
				.category(Category.MUSICAL)
				.build(),
			GetProductsResDto.Product.builder()
				.image("https://s3.atticket.com/products/images/cats")
				.id(2L)
				.name("뮤지컬 〈캣츠〉 오리지널 내한－대전（Musical CATS）")
				.place(GetProductsResDto.Place.builder()
					.id(2L)
					.name("대전예술의전당")
					.build()
				)
				.startDate(LocalDate.of(2023, 05, 19))
				.endDate(LocalDate.of(2023, 05, 21))
				.runningTime(LocalTime.of(2, 40))
				.interMission(LocalTime.of(0, 20))
				.ageLimit(AgeLimit.EIGHT)
				.category(Category.MUSICAL)
				.build(),
			GetProductsResDto.Product.builder()
				.image("https://s3.atticket.com/products/images/cats")
				.id(3L)
				.name("뮤지컬 〈캣츠〉 오리지널 내한－수원（Musical CATS）")
				.place(GetProductsResDto.Place.builder()
					.id(3L)
					.name("경기아트센터")
					.build()
				)
				.startDate(LocalDate.of(2023, 05, 12))
				.endDate(LocalDate.of(2023, 05, 14))
				.runningTime(LocalTime.of(2, 40))
				.interMission(LocalTime.of(0, 20))
				.ageLimit(AgeLimit.EIGHT)
				.category(Category.MUSICAL)
				.build())
		).build());
	}

	/**
	 * 상품 상세 조회
	 */
	@GetMapping("/{productId}")

	public BaseResponse<GetProductResDto> getProduct(@PathVariable("productId") Long id) {

		log.debug("getProduct - id : " + id);

		return ok(GetProductResDto.construct(productService.getProductById(id),
			gradeService.getGradesByProductId(id), showService.getShowDatesByProductId(id)));
	}

	//일자별 공연 조회
	@GetMapping("/{productId}/shows")
	public BaseResponse<GetShowsResDto> getShows(@PathVariable("productId") Long productId,
		@RequestParam(name = "date") String inputDate) throws Exception {

		log.debug("getShowList - productId : " + productId);
		log.debug("getShowList - date : " + inputDate);

		LocalDate paredInputDate;

		//LocalDate로  입력 날짜 파싱
		try {
			paredInputDate = LocalDate.parse(inputDate, DateTimeFormatter.BASIC_ISO_DATE);
		} catch (Exception e) {
			throw new BaseException(BaseStatus.DATE_PATTERN);
		}

		//날짜의 공연 리스트 조회
		List<Show> shows = showService.getShowDateByProductId(productId, paredInputDate);

		return ok(GetShowsResDto.construct(shows));
	}

	/**
	 * 상품 삭제
	 * */
	@DeleteMapping("/{productId}")
	public BaseResponse deleteProduct(@PathVariable("productId") Long id) {

		productService.deleteProduct(id);
		return ok();
	}

}
