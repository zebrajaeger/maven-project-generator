module.exports = function (config, gulp, plugins) {

    var autoprefixer = require('autoprefixer');

    var srcFiles = plugins.path.resolve(config.paths.src, 'styles/app.scss');
    var resultPath = plugins.path.resolve(config.paths.tmp, 'css');

    return function processCompile() {
        return gulp
            .src(srcFiles)
            .pipe(plugins.plumber())

            .pipe(plugins.prod(plugins.sourcemaps.init()))

            .pipe(plugins.sassGlob())
            .pipe(plugins.sass(config.sass).on('error', plugins.sass.logError))
            .pipe(plugins.postcss([autoprefixer(config.autoprefixer)]))
            .pipe(gulp.dest(resultPath))

            .pipe(plugins.prod(plugins.cssnano()))
            .pipe(plugins.prod(plugins.rename('app.min.css')))
            .pipe(plugins.prod(plugins.sourcemaps.write(resultPath, {
                sourceMappingURL: function (file) {
                    return file.relative + '.map';
                }
            })))
            .pipe(plugins.prod(gulp.dest(resultPath)));
    }
};
