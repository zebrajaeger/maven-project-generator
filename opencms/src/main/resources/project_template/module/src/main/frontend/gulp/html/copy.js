module.exports = function (config, gulp, plugins) {
    return function () {
        var source = plugins.path.resolve(config.paths.tmp, 'html/**/*.html');

        return gulp
            .src(source)
            .pipe(plugins.plumber())

            .pipe(gulp.dest(plugins.path.resolve(config.paths.env, 'build')));
    };
};
