package com.atticket.product.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetProductsResDto {

	private final List<Product> productList;

	@Builder
	@Getter
	public static class Product {

		private final String category;

		private final String id;

		private final String name;

		private final String image;

		private final Place place;

		private final String startDate;

		private final String endDate;

		private final String runningTime;

		private final String ageLimit;
	}

	@Builder
	@Getter
	public static class Place {

		private final String id;

		private final String name;
	}
}
