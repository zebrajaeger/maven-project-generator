module.exports = function (config, gulp, plugins, options) {
    return function processCompile() {
        var name = options.name;
        var sourceDir = [
            plugins.path.resolve(config.paths.src, 'js', name, "**/*.js"),
            plugins.path.resolve(config.paths.src, 'js', name + ".js")
        ];
        var targetDir = plugins.path.resolve(config.paths.tmp, 'js');

        return gulp
            .src(sourceDir)
            .pipe(plugins.plumber())

            .pipe(plugins.prod(plugins.sourcemaps.init()))

            .pipe(plugins.concat(name + '.js'))
            .pipe(gulp.dest(targetDir))

            .pipe(plugins.prod(plugins.uglify()))
            .pipe(plugins.prod(plugins.rename(name + '.min.js')))
            .pipe(plugins.prod(plugins.sourcemaps.write(targetDir, {
                sourceMappingURL: function (file) {
                    return file.relative + '.map';
                }
            })))
            .pipe(plugins.prod(gulp.dest(targetDir)));
    }
};
