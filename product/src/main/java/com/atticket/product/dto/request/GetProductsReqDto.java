package com.atticket.product.dto.request;

import com.atticket.product.type.Category;
import com.atticket.product.type.Region;
import com.atticket.product.type.SortOption;
import com.atticket.product.type.SubCategory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Positive;
import java.time.LocalDate;

import static com.atticket.common.response.BaseException.throwTypeMismatchIfNull;
import static com.atticket.common.utils.Constants.DATE_PATTERN;

@Getter
@Setter
public class GetProductsReqDto {

	// 페이지 (default = 1)
	@Positive
	private int page = 1;

	// 페이지당 상품 개수 (default = 10)
	@Positive
	private int perPage = 10;

	// 검색어
	private String keyword;

	// 도메인 카테고리 (e.g. 뮤지컬, 연극, 콘서트, 영화)
	private Category category;

	// 도메인별 세부 장르 (e.g. 뮤지컬 - [라이선스, 창작 뮤지컬, ...], 콘서트 - [발라드, 내한공연, 페스티벌, ...], ...)
	private SubCategory subCategory;

	// 지역 (e.g. 서울, 경기, 대전, 광주, ... )
	private Region region;

	// 시작일
	@DateTimeFormat(pattern = DATE_PATTERN)
	private LocalDate startDate;

	// 종료일
	@DateTimeFormat(pattern = DATE_PATTERN)
	private LocalDate endDate;

	// 정렬 옵션  (default = SortOption.RECENT)  (e.g. 상품명순, 종료임박순, 판매량순, 최신순)
	private SortOption sortOption = SortOption.NAME;

	public void setSortOption(SortOption sortOption) {
		// sortOption은 null이면 안되기 때문에 typeMismatch 에러를 날린다
		throwTypeMismatchIfNull(sortOption);
		this.sortOption = sortOption;
	}
}
