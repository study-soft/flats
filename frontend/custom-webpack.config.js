const webpack = require('webpack');

console.log(">>>> PORT = ", process.env.PORT);

module.exports = {
  plugins: [
    new webpack.DefinePlugin({
      $ENV: {
        PORT: JSON.stringify(process.env.PORT),
      }
    })
  ]
};
