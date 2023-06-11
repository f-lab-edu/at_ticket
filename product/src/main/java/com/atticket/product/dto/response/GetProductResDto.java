package com.atticket.product.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.atticket.product.domain.Grade;
import com.atticket.product.domain.Product;
import com.atticket.product.type.AgeLimit;
import com.atticket.product.type.Category;
import com.atticket.product.type.Region;
import com.atticket.product.type.SubCategory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetProductResDto {

	//상품 세부 정보
	private final ProductDto product;
	//좌석 type List
	private final List<GradeDto> seatGrades;
	//List<공연 상연일>
	private final List<LocalDate> showDates;

	public static GetProductResDto construct(Product product, List<Grade> grades, List<LocalDate> showDates) {
		if (Objects.isNull(product)) {
			return null;
		}
		return new GetProductResDto(ProductDto.construct(product), grades.stream().map(GradeDto::construct).collect(
			Collectors.toList()), showDates);
	}

	@Getter
	@Builder
	public static class ProductDto {

		private final String name;
		//상품 설명
		private final String explain;
		//카테고리
		private final Category category;
		//하위 카테고리
		private final SubCategory subCategory;
		//러닝 타임
		private final LocalTime runningTime;
		//상연 시작 일자
		private final LocalDate startDate;
		//상연 종료 일자
		private final LocalDate endDate;
		//나이 제한
		private final AgeLimit ageLimit;
		//이미지
		private final String image;
		//장소 정보
		private final Region region;

		private static ProductDto construct(Product product) {
			return ProductDto.builder()
				.category(product.getCategory())
				.subCategory(product.getSubCategory())
				.name(product.getName())
				.explain(product.getExplains())
				.runningTime(product.getRunningTime())
				.startDate(product.getStartDate())
				.endDate(product.getEndDate())
				.ageLimit(product.getAgeLimit())
				.image(product.getImage())
				.region(product.getPlace().getRegion())
				.build();
		}
	}

	//좌석 등급
	@Getter
	@Builder
	public static class GradeDto {

		//좌석 타입
		private final String type;
		//좌석 가격
		private final int price;

		private static GradeDto construct(Grade grade) {
			return GradeDto.builder()
				.type(grade.getType())
				.price(grade.getPrice())
				.build();
		}
	}
}
