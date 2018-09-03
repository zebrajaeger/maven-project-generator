module.exports = function (config, gulp, plugins, options) {
    return function () {
        var name = options.name;

        var files = [
            plugins.path.resolve(config.paths.tmp, 'js/' + name + '.js'),
            plugins.path.resolve(config.paths.tmp, 'js/' + name + '.min.js'),
            plugins.path.resolve(config.paths.tmp, 'js/' + name + '.min.js.map'),
        ];

        return gulp
            .src(files)
            .pipe(plugins.plumber())

            .pipe(gulp.dest(plugins.path.resolve(config.paths.env, 'build/js')))
            .pipe(gulp.dest(plugins.path.resolve(config.paths.env, 'dist/js')));
    };
};
