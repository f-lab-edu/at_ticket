package com.atticket.product.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.atticket.product.type.AgeLimit;
import com.atticket.product.type.Category;
import com.atticket.product.type.Region;
import com.atticket.product.type.SubCategory;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetProductResDto {

	//상품 세부 정보
	private final Product product;

	//좌석 Type List
	private final List<Grade> seatGrades;

	//List<공연 상연일>
	private final List<LocalDate> showDates;

	@Getter
	@Builder
	public static class Product {

		private String name;

		//상품 설명
		private String explain;

		//카테고리
		private Category category;

		//하위 카테고리
		private SubCategory subCategory;

		//러닝 타임
		private LocalTime runningTime;

		//상연 시작 일자
		private LocalDate startDate;

		//상연 종료 일자
		private LocalDate endDate;

		//나이 제한
		private AgeLimit ageLimit;

		//이미지
		private String image;

		//장소 정보
		private Region region;

	}

	//좌석 등급
	@Getter
	@Builder
	public static class Grade {

		//좌석 타입
		private String type;

		//좌석 가격
		private int price;

	}

}
