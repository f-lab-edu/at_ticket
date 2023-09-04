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
      "/products",
      {
        target: "http://localhost:8000",
        changeOrigin: true,
      },
    ],
    [
      "/shows",
      {
        target: "http://localhost:8000",
        changeOrigin: true,
      },
    ],
    [
      "/reservations",
      {
        target: "http://localhost:8100",
        changeOrigin: true,
      },
    ],
    [
      "/users",
      {
        target: "http://localhost:9000",
        changeOrigin: true,
      },
    ],
  ],

  plugins: ["@/plugins/persisted-state.client.js", "~/plugins/lodash.js"],
};
