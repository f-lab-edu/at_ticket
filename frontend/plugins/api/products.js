export default function ({ app }, inject) {
	/**
	 * 상품 리스트 조회
	 * @param data {{keyword: String}}
	 */
	const getProducts = (data) => {
		return app.$axios
			.get("/api/products", {
				params: data,
			})
			.then((d) => d.data)
			.catch((e) => e.response.data);
	};
	inject("getProducts", getProducts);

	/**
	 *	상품 상세 조회
	 *	@param data - id (상품 id)
	 */
	const getProduct = (data) => {
		return app.$axios
			.get(`/api/products/${data}`)
			.then((d) => d.data)
			.catch((e) => e.response.data);
	};
	inject("getProduct", getProduct);
}
