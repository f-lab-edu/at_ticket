export default {
	ssr: false,

	target: "static",

	head: {
		script: [
			{
				src: "https://code.jquery.com/jquery-1.12.4.min.js",
			},
			{
				src: "https://cdn.iamport.kr/js/iamport.payment-1.2.0.js",
			},
		],
	},

	modules: ["@nuxtjs/axios", "@nuxtjs/proxy"],

	proxy: [
		[
			"/api/products",
			{
				target: "http://localhost:8000",
				changeOrigin: true,
				pathRewrite: {
					"/api": "",
				},
			},
		],
		[
			"/api/shows",
			{
				target: "http://localhost:8000",
				changeOrigin: true,
				pathRewrite: {
					"/api": "",
				},
			},
		],
		[
			"/api/reservations",
			{
				target: "http://localhost:8100",
				changeOrigin: true,
				pathRewrite: {
					"/api": "",
				},
			},
		],
		[
			"/api/users",
			{
				target: "http://localhost:9000",
				changeOrigin: true,
				pathRewrite: {
					"/api": "",
				},
			},
		],
	],

	plugins: [
		"@/plugins/persisted-state.client.js",
		"@/plugins/lodash.js",
		"@/plugins/checkToken.js",
		"@/plugins/date-picker.js",
		"@/plugins/api/users.js",
		"@/plugins/api/products.js",
		"@/plugins/api/shows.js",
	],
};
