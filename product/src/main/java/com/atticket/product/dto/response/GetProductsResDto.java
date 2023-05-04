package com.atticket.product.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.atticket.product.type.AgeLimit;
import com.atticket.product.type.Category;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetProductsResDto {

	private final List<Product> productList;

	@Builder
	@Getter
	public static class Product {

		private Category category;

		private Long id;

		private String name;

		private String image;

		private Place place;

		private LocalDate startDate;

		private LocalDate endDate;

		private LocalTime runningTime;

		private LocalTime interMission;

		private AgeLimit ageLimit;
	}

	@Builder
	@Getter
	public static class Place {

		private Long id;

		private String name;
	}
}
