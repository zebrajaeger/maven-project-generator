module.exports = function (config, gulp, plugins, options) {
    return function () {
        var name = options.name;
        var files = [
            plugins.path.resolve(config.paths.tmp, 'css/' + name + '.css'),
            plugins.path.resolve(config.paths.tmp, 'css/' + name + '.min.css'),
            plugins.path.resolve(config.paths.tmp, 'css/' + name + '.min.css.map'),
        ];

        return gulp
            .src(files)
            .pipe(plugins.plumber())

            .pipe(gulp.dest(plugins.path.resolve(config.paths.env, 'build/css')))
            .pipe(gulp.dest(plugins.path.resolve(config.paths.env, 'dist/css')));
    };
};
