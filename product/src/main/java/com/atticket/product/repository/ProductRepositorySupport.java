package com.atticket.product.repository;

import com.atticket.product.domain.Product;
import com.atticket.product.type.Category;
import com.atticket.product.type.Region;
import com.atticket.product.type.SortOption;
import com.atticket.product.type.SubCategory;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static com.atticket.product.domain.QProduct.product;

//@Repository
//public class ProductRepositorySupport extends QuerydslRepositorySupport {
//	private final JPAQueryFactory queryFactory;
//
//	public ProductRepositorySupport(JPAQueryFactory queryFactory) {
//		super(Product.class);
//		this.queryFactory = queryFactory;
//	}
//
//	public List<Product> findByName(String name) {
//		return queryFactory
//			.selectFrom(product)
//			.where(product.name.eq(name))
//			.fetch();
//	}
//}

@RequiredArgsConstructor
@Repository
public class ProductRepositorySupport {
	private final JPAQueryFactory queryFactory;

	public List<Product> getProduct(int page, int perPage, String keyword, Category category,
									SubCategory subCategory, Region region, LocalDate startDate, LocalDate endDate,
									SortOption sortOption) {
		return queryFactory
			.selectFrom(product)
			.where(containKeyword(keyword), eqCategory(category), eqSubCategory(subCategory), eqRegion(region),
				goeStartDate(startDate), loeEndDate(endDate)
			)
			.offset((long) (page - 1) * perPage)
			.orderBy(setSortOption(sortOption))
			.limit(perPage)
			.fetch();
	}

	private BooleanExpression containKeyword(String keyword) {
		if (!StringUtils.hasText(keyword)) {
			return null;
		}

		return product.name.contains(keyword);
	}

	private BooleanExpression eqCategory(Category category) {
		if (Objects.isNull(category)) {
			return null;
		}

		return product.category.eq(category);
	}

	private BooleanExpression eqSubCategory(SubCategory subCategory) {
		if (Objects.isNull(subCategory)) {
			return null;
		}

		return product.subCategory.eq(subCategory);
	}

	private BooleanExpression eqRegion(Region region) {
		if (Objects.isNull(region)) {
			return null;
		}

		return product.place.region.eq(region);
	}

	private BooleanExpression goeStartDate(LocalDate startDate) {
		if (Objects.isNull(startDate)) {
			return null;
		}

		return product.endDate.goe(startDate);
	}

	private BooleanExpression loeEndDate(LocalDate endDate) {
		if (Objects.isNull(endDate)) {
			return null;
		}

		return product.startDate.loe(endDate);
	}

	private OrderSpecifier setSortOption(SortOption sortOption) {
		if (sortOption.equals(SortOption.NAME)) {
			return product.name.asc();
		}

		if (sortOption.equals(SortOption.RECENT)) {
			return product.startDate.desc();
		}

		if (sortOption.equals(SortOption.END_DATE)) {
			return product.endDate.asc();
		}

		return product.name.asc();
	}
}
