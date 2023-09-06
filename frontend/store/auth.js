export const state = () => ({
	auth: {
		token: {},
		loggedIn: false,
		user: {},
	},
});

export const getters = {
	auth: (state) => state.auth,
};

export const mutations = {
	setAuth(state, data) {
		state.auth = {
			...state.auth,
			...data,
		};
	},
	clearAuth(state) {
		state.auth = {
			token: {},
			loggedIn: false,
			user: {},
		};
	},
};

export const actions = {
	/**
	 * @param data {{username: String, password: String}}
	 * */
	async login({ commit }, data) {
		const tokenRes = await this.$getToken(data);

		if (tokenRes.code !== 200) {
			commit("clearAuth");
			return false;
		}

		const userRes = await this.$getUser(tokenRes.data.access_token);

		if (userRes.code !== 200) {
			commit("setAuth", {
				token: tokenRes.data,
			});
			return false;
		}

		commit("setAuth", {
			token: tokenRes.data,
			loggedIn: true,
			user: userRes.data,
		});

		return true;
	},
	logout({ commit }) {
		commit("clearAuth");
	},
};
