const webpack = require('webpack');

module.exports = {
  plugins: [
    new webpack.DefinePlugin({
      $ENV: {
        SERVER_API_URL: JSON.stringify(process.env.SERVER_API_URL),
      }
    })
  ]
};
