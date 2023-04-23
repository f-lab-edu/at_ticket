package com.atticket.product.domain;

import java.time.LocalDate;
import java.time.LocalTime;

import com.atticket.product.type.AgeLimit;
import com.atticket.product.type.Category;
import com.atticket.product.type.Region;
import com.atticket.product.type.SubCategory;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Setter
public class Product {

	//상품 Id
	private Long id;
	//상품 이름
	private String name;
	//상품 설명
	private String explain;
	//도메인 카테고리
	private Category category;
	//도메인별 세부 장르
	private SubCategory subCategory;
	//러닝 타임
	private LocalTime runningTime;
	//인터 미션
	private LocalTime interMission;
	//지역
	private Region region;
	//상연 시작 일자
	private LocalDate startDate;
	//상연 종료 일자
	private LocalDate endDate;
	//이미지
	private String image;
	//나이 제한
	private AgeLimit ageLimit;
	//장소 Id
	private Long placeId;
}
