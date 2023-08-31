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
                },
                "/reservations": {
                    target: "http://localhost:8100",
                    changeOrigin: true,
                },
                "/users": {
                    target: "http://localhost:9000",
                    changeOrigin: true,
                },
            },
        },
    },
    devtools: {enabled: true},


})
