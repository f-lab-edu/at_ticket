export default function ({ app }, inject) {
	const USER_API_PREFIX = "/api/users";
	/**
	 * @param data {{username: String, password: String}}
	 * */
	const getToken = ({ username, password }) => {
		return app.$axios
			.post(`${USER_API_PREFIX}/token`, { username, password })
			.then((d) => d.data)
			.catch((e) => e.response.data);
	};
	inject("getToken", getToken);

	/**
	 * @param data {{accessToken: String}}
	 * */
	const getUser = ({ accessToken }) => {
		return app.$axios
			.get(`${USER_API_PREFIX}`, {
				headers: {
					Authorization: accessToken,
				},
			})
			.then((d) => d.data)
			.catch((e) => e.response.data);
	};
	inject("getUser", getUser);
}
