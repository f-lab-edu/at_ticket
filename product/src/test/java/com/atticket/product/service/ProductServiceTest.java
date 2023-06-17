package com.atticket.product.service;

import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.atticket.common.response.BaseException;
import com.atticket.common.response.BaseStatus;
import com.atticket.product.domain.Product;
import com.atticket.product.repository.ProductRepository;
import com.atticket.product.type.Category;
import com.atticket.product.type.SubCategory;

public class ProductServiceTest {

	//service
	private ProductService productService;
	private ShowService showService = mock(ShowService.class);
	private GradeService gradeService = mock(GradeService.class);
	private ReservedSeatService reservedSeatService = mock(ReservedSeatService.class);
	private ShowSeatService showSeatService = mock(ShowSeatService.class);

	//repository
	private ProductRepository productRepository = mock(ProductRepository.class);

	@BeforeEach
	public void setUpTest() {
		productService = new ProductService(showService, gradeService, reservedSeatService, showSeatService,
			productRepository);
	}

	@Test
	@DisplayName("상품 조회 ")
	void getProductById() {

		//Given
		Long productId = 1L;
		Product givenProduct = Product.builder().build();

		//When
		when(productRepository.findById(productId)).thenReturn(Optional.of(givenProduct));
		Product result = productService.getProductById(productId);

		//Then
		Assertions.assertEquals(result, givenProduct);
		verify(productRepository).findById(1L);

	}

	@Test
	@DisplayName("상품 삭제")
	void deleteProduct() {

		//Given
		Long productId = 1L;
		Product givenProduct = Product.builder().build();

		//When
		when(productRepository.findById(productId)).thenReturn(Optional.of(givenProduct));
		productService.deleteProduct(productId);

		//Then
		verify(productRepository).deleteById(productId);
	}

	@Test
	@DisplayName("상품 삭제 에러")
	void deleteProductNotFindId() {

		//Given
		Long productId = 1L;

		//When
		when(productRepository.findById(productId)).thenReturn(null);

		//Then
		Assertions.assertThrows(BaseException.class, () -> productService.deleteProduct(productId));
	}

	@ParameterizedTest
	@MethodSource("getProducts_throw_SUB_CATEGORY_DOES_NOT_IN_CATEGORY_exception_param")
	@DisplayName("카테고리와 서브카테고리 관계가 이상하면 exception을 날린다")
	void getProducts_throw_SUB_CATEGORY_DOES_NOT_IN_CATEGORY_exception(Category category, SubCategory subCategory) {
		//when
		BaseException exception = Assertions.assertThrows(BaseException.class,
			() -> productService.getProducts(1, 1, null, category,
				subCategory, null, null, null, null));
		//then
		Assertions.assertEquals(exception.getMessage(), BaseStatus.SUB_CATEGORY_DOES_NOT_IN_CATEGORY.getMessage());
	}

	static Stream<Arguments> getProducts_throw_SUB_CATEGORY_DOES_NOT_IN_CATEGORY_exception_param() {
		List<Arguments> params = new ArrayList<>();
		Arrays.stream(Category.values())
			.forEach(ca -> Arrays.stream(SubCategory.values()).filter(subCa -> !ca.getSubCategories().contains(subCa))
				.forEach(subCa -> params.add(arguments(ca, subCa))));
		return params.stream();
	}
}
