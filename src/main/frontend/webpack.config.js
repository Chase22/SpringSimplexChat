var path = require('path');

module.exports = {
    mode: 'development',
    entry: './src/chatview.js',
    output: {
        path: path.resolve(__dirname, '../resources/static/js'),
        filename: 'chatview.bundle.js'
    },
    module: {
        rules: [
            {
                test: /\.js$/,
                loader: 'babel-loader',
                query: {
                    // presets: ['es2015']
                }
            },
            {
                test: /\.css$/,
                use: ['style-loader', 'css-loader'],
            }
        ]
    },
    stats: {
        colors: true
    },
    devtool: 'source-map'
};
