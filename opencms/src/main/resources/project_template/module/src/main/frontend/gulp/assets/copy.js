module.exports = function (config, gulp, plugins) {
    return function () {
        var files = [
            //plugins.path.resolve(config.paths.tmp, 'assets/**/*'),
            plugins.path.resolve(config.paths.src, 'assets/**/*')
        ];

        return gulp
            .src(files)
            .pipe(plugins.plumber())

            .pipe(plugins.dev(gulp.dest(plugins.path.resolve(config.paths.env, 'build/assets'))))
            .pipe(plugins.prod(gulp.dest(plugins.path.resolve(config.paths.env, 'dist/assets'))));
    };
};
