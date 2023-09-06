export default function ({ app }, inject) {
	/**
	 * @param data {{username: String, password: String}}
	 * */
	const getToken = (data) => {
		return app.$axios
			.post(`/api/users/token`, data)
			.then((d) => d.data)
			.catch((e) => e.response.data);
	};
	inject("getToken", getToken);

	/**
	 * @param data - access_token
	 * */
	const getUser = (data) => {
		return app.$axios
			.get(`/api/users`, {
				headers: {
					Authorization: data,
				},
			})
			.then((d) => d.data)
			.catch((e) => e.response.data);
	};
	inject("getUser", getUser);
}
