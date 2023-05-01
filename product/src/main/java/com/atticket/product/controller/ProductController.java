package com.atticket.product.controller;

import static com.atticket.common.response.BaseResponse.ok;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
import com.atticket.product.domain.Grade;
import com.atticket.product.domain.Product;
import com.atticket.product.domain.Show;
import com.atticket.product.dto.request.GetProductsReqDto;
import com.atticket.product.dto.response.GetProductResDto;
import com.atticket.product.dto.response.GetProductsResDto;
import com.atticket.product.dto.response.GetShowsResDto;
import com.atticket.product.repository.GradeRepository;
import com.atticket.product.repository.ShowRepository;
import com.atticket.product.service.ProductService;
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
	private final ShowRepository showRepository;
	private final GradeRepository gradeRepository;

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

	public BaseResponse<GetProductResDto> getProduct(@PathVariable("productId") Long id) throws
		Exception {

		log.debug("getProduct - id : " + id);

		if (id.equals("error")) {
			throw new Exception("unexpected error our");
		}
		if (id.equals("test-error")) {
			throw new BaseException(BaseStatus.TEST_ERROR);
		}

		//상품 정보
		Product product = productService.getProductById(id);

		if (product == null) {
			return ok(
				GetProductResDto.builder().build()
			);
		}

		//공연 정보
		List<Show> shows = showRepository.findShowsByProductId(id);
		//공연 정보에서 Date정보만 뽑기
		List<LocalDate> showDateList = shows.stream().map(Show::getDate).collect(Collectors.toList());

		//등급 정보
		List<Grade> grades = gradeRepository.findGradeByProductId(id);
		List<GetProductResDto.Grade> gradeList = new ArrayList<>();

		for (Grade grade : grades) {
			gradeList.add(
				GetProductResDto.Grade.builder()
					.price(grade.getPrice())
					.type(grade.getType())
					.build()
			);
		}

		return ok(
			GetProductResDto.builder()
				.product(
					GetProductResDto.Product.builder()
						.category(product.getCategory())
						.subCategory(product.getSubCategory())
						.name(product.getName())
						.explain(product.getExplain())
						.runningTime(product.getRunningTime())
						.startDate(product.getStartDate())
						.endDate(product.getEndDate())
						.ageLimit(product.getAgeLimit())
						.image(product.getImage())
						.region(product.getRegion())
						.build()
				)
				.seatGrades(gradeList)
				.showDates(
					showDateList
				).build()
		);
	}

	//일자별 공연 조회
	@GetMapping("/{productId}/shows")
	public BaseResponse<GetShowsResDto> getShows(@PathVariable("productId") Long productId,
		@RequestParam("date") String inputDate) throws Exception {

		log.debug("getShowList - productId : " + productId);
		log.debug("getShowList - date : " + inputDate);

		//LocalDate로  입력 날짜 파싱
		DateTimeFormatter formatter = DateTimeFormatter.BASIC_ISO_DATE;
		LocalDate paredDate = LocalDate.parse(inputDate, formatter);

		//공연 정보
		List<Show> shows = showRepository.findShowsByProductId(productId);

		//날짜로 필터링
		shows = shows.stream().filter(
			show -> show.getDate().equals(paredDate)
		).collect(Collectors.toList());

		List<GetShowsResDto.Show> showList = new ArrayList<>();

		for (Show show : shows) {

			showList.add(
				GetShowsResDto.Show.builder()
					.id(show.getId())
					.session(show.getSession())
					.time(show.getTime())
					.build()
			);
		}

		return ok(
			GetShowsResDto.builder()
				.shows(
					showList
				).build()
		);
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
