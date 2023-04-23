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

		private final Category category;

		private final Long id;

		private final String name;

		private final String image;

		private final Place place;

		private final LocalDate startDate;

		private final LocalDate endDate;

		private final LocalTime runningTime;

		private final LocalTime interMission;

		private final AgeLimit ageLimit;
	}

	@Builder
	@Getter
	public static class Place {

		private final Long id;

		private final String name;
	}
}
