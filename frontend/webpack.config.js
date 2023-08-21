const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');

module.exports = {
    mode: 'development',
    entry: './src/index.js',
    output: {
        filename: 'main.js',
        path: `${__dirname}/dist`,
    },
    devServer: {
        static: './dist',
        proxy: {
            "/products": {
                target: "http://localhost:8000",
                changeOrigin: true,
            }
        }
    },
    plugins: [new HtmlWebpackPlugin({
        template: './src/index.html'
    })],
};
