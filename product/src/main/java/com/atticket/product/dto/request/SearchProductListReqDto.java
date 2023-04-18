package com.atticket.product.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class SearchProductListReqDto {

	// 페이지
	private final String page;

	// 페이지당 상품 개수
	private final String perPage;

	// 검색어
	private final String keyword;

	// 도메인 카테고리 (e.g. 뮤지컬, 연극, 콘서트, 영화)
	private final String category;

	// 도메인별 세부 장르 (e.g. 뮤지컬 - [라이선스, 창작 뮤지컬, ...], 콘서트 - [발라드, 내한공연, 페스티벌, ...], ...)
	private final String subCategory;

	// 지역 (e.g. 서울, 경기, 대전, 광주, ... )
	private final String region;

	// 시작일
	private final String startDate;

	// 종료일
	private final String endDate;

	// 정렬 옵션 (e.g. 상품명순, 종료임박순, 판매량순, 최신순)
	private final String sortOption;
}
