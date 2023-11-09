package com.atticket.product.service;

import com.atticket.common.response.BaseException;
import com.atticket.common.response.BaseStatus;
import com.atticket.product.domain.Product;
import com.atticket.product.domain.Show;
import com.atticket.product.repository.ProductRepository;
import com.atticket.product.repository.ProductRepositorySupport;
import com.atticket.product.type.Category;
import com.atticket.product.type.Region;
import com.atticket.product.type.SortOption;
import com.atticket.product.type.SubCategory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static com.atticket.common.response.BaseStatus.EXIST_RESERVED;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

	//service
	private final ShowService showService;
	private final GradeService gradeService;
	private final ReservedSeatService reservedSeatService;
	private final ShowSeatService showSeatService;

	//repository
	private final ProductRepository productRepository;

	private final ProductRepositorySupport productRepositorySupport;

	/**
	 * 상품id로 상품 조회
	 *
	 * @param productId 상품 id
	 * @return Product
	 */
	public Product getProductById(Long productId) {

		return productRepository.findById(productId).orElse(null);

	}

	/**
	 * 상품 삭제
	 *
	 * @param productId 상품 id
	 */

	@Transactional
	public void deleteProduct(Long productId) {

		Product product = productRepository.findById(productId).orElse(null);

		//해당 id 상품이 없으면 Exception
		if (Objects.isNull(product)) {
			throw new BaseException(BaseStatus.INVALID_PRODUCT);
		} else {

			List<Show> shows = showService.getShowsByProductId(product.getId());

			for (Show show : shows) {
				if (reservedSeatService.getReservedSeatIdsByShowId(show.getId()).size() > 0) {
					//show의 예약이 존재하면  Exception
					throw new BaseException(EXIST_RESERVED);
				} else {
					//없으면 좌석-show 매핑삭제
					showSeatService.deleteByShow(show);
				}
			}
			showService.deleteByProduct(product);
			gradeService.deleteByProduct(product);
		}

		productRepository.deleteById(productId);
	}

	/**
	 * @param page        페이지
	 * @param perPage     페이지당 상품 개수
	 * @param keyword     검색어
	 * @param category    도메인 카테고리 (e.g. 뮤지컬, 연극, 콘서트, 영화)
	 * @param subCategory 도메인별 세부 장르 (e.g. 뮤지컬 - [라이선스, 창작 뮤지컬, ...], 콘서트 - [발라드, 내한공연, 페스티벌, ...])
	 * @param region      지역 (e.g. 서울, 경기, 대전, 광주, ... )
	 * @param startDate   시작일
	 * @param endDate     종료일
	 * @param sortOption  정렬 옵션 (e.g. 상품명순, 종료임박순, 판매량순, 최신순)
	 * @return 각 파라미터 필터를 적용한 상품 Entity 리스트를 리턴한다.
	 */
	public List<Product> getProducts(int page, int perPage, String keyword, Category category,
									 SubCategory subCategory, Region region, LocalDate startDate, LocalDate endDate,
									 SortOption sortOption) {

		categoryHasSubCategory(category, subCategory);

		return productRepositorySupport.getProduct(page, perPage, keyword, category, subCategory,
			region, startDate, endDate, sortOption);
	}

	private void categoryHasSubCategory(Category category, SubCategory subCategory) {
		if (Objects.nonNull(category) && Objects.nonNull(subCategory) && !category.hasSub(subCategory)) {
			throw new BaseException(BaseStatus.SUB_CATEGORY_DOES_NOT_IN_CATEGORY);
		}
	}
}
