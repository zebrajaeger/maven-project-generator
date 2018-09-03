module.exports = function(config, gulp, plugins) {
    return function () {

        var srcFiles = plugins.path.resolve(config.paths.src, 'styles/vendor/*.css');
        var resultPath = plugins.path.resolve(config.paths.tmp, 'css');

        return gulp
            .src(srcFiles)
            .pipe(plugins.plumber())

            .pipe(plugins.prod(plugins.sourcemaps.init()))

            .pipe(plugins.concat('vendor.css'))
            .pipe(gulp.dest(resultPath))

            .pipe(plugins.prod(plugins.cssnano()))
            .pipe(plugins.prod(plugins.rename('vendor.min.css')))
            .pipe(plugins.prod(plugins.sourcemaps.write(resultPath, {
                sourceMappingURL: function (file) {
                    return file.relative + '.map';
                }
            })))
            .pipe(plugins.prod(gulp.dest(resultPath)));
    }
};
