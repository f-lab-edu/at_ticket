// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({

    app: {
        head: {
            script: [
                {
                    src: 'https://code.jquery.com/jquery-1.12.4.min.js',
                },
                {
                    src: 'https://cdn.iamport.kr/js/iamport.payment-1.2.0.js',
                },
                {
                    hid: "kakaoSDK",
                    src: "https://developers.kakao.com/sdk/js/kakao.min.js",
                    defer: true
                },
                {
                    hid: "google",
                    src: "https://accounts.google.com/gsi/client",
                    defer: true,
                    sync: true
                }
            ],
        }
    },

    vite: {
        server: {
            proxy: {
                "/products": {
                    target: "http://localhost:8000",
                    changeOrigin: true,
                },
                "/shows": {
                    target: "http://localhost:8000",
                    changeOrigin: true,
                }
            },
        },
    },
    devtools: {enabled: true},


})
