export default function ({ app }, inject) {
	const PRODUCT_API_PREFIX = "/api/products";
	/**
	 * 상품 리스트 조회
	 * @param data {{keyword: String}}
	 */
	const getProducts = ({ keyword }) => {
		return app.$axios
			.get(`${PRODUCT_API_PREFIX}`, {
				params: keyword,
			})
			.then((d) => d.data)
			.catch((e) => e.response.data);
	};
	inject("getProducts", getProducts);

	/**
	 *	상품 상세 조회
	 *	@param data {{id:String}} (상품 id)
	 */
	const getProduct = ({ id }) => {
		return app.$axios
			.get(`${PRODUCT_API_PREFIX}/${id}`)
			.then((d) => d.data)
			.catch((e) => e.response.data);
	};
	inject("getProduct", getProduct);

	/**
	 *	일자별 공연 조회
	 *	@param data {{productId: String, date: String}}
	 * 	- productId : 상품 id
	 * 	- date : 일자 (YYYY-MM-DD)
	 */
	const getShows = ({ productId, date }) => {
		return app.$axios
			.get(`${PRODUCT_API_PREFIX}/${productId}/shows?date=${date}`)
			.then((d) => d.data)
			.catch((e) => e.response.data);
	};
	inject("getShows", getShows);
}
