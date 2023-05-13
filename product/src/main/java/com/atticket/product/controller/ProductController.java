package com.atticket.product.controller;

import static com.atticket.common.response.BaseResponse.ok;
import static com.atticket.common.utils.Constants.DATE_PATTERN;

import java.time.LocalDate;

import javax.validation.Valid;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.atticket.common.response.BaseResponse;
import com.atticket.product.dto.request.GetProductsReqDto;
import com.atticket.product.dto.response.GetProductResDto;
import com.atticket.product.dto.response.GetProductsResDto;
import com.atticket.product.dto.response.GetShowsResDto;
import com.atticket.product.service.GradeService;
import com.atticket.product.service.ProductService;
import com.atticket.product.service.ShowService;

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
	public BaseResponse<GetProductsResDto> getProducts(@Valid GetProductsReqDto reqDto) {

		return ok(GetProductsResDto.construct(
			productService.getProducts(reqDto.getPage(), reqDto.getPerPage(), reqDto.getKeyword(),
				reqDto.getCategory(), reqDto.getSubCategory(), reqDto.getRegion(), reqDto.getStartDate(),
				reqDto.getEndDate(), reqDto.getSortOption())));
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
	public BaseResponse<GetShowsResDto> getShows(
		@PathVariable("productId") Long productId,
		@DateTimeFormat(pattern = DATE_PATTERN) @RequestParam(name = "date") LocalDate date) {

		log.debug("getShowList - productId : " + productId);
		log.debug("getShowList - date : " + date);

		return ok(GetShowsResDto.construct(showService.getShowDateByProductId(productId, date)));
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
