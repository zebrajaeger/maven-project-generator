module.exports = function (paths, gulp, plugins) {
    return function () {
        return gulp
            .src(plugins.path.resolve(paths.src, 'styles/scss/**/*.scss'))
            .pipe(plugins.plumber())

            .pipe(plugins.stylelint({
                failAfterError: true,
                reporters: [
                    {formatter: 'string', console: true}
                ]
            }));
    }
};
