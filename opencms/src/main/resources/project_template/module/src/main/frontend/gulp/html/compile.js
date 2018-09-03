module.exports = function (config, gulp, plugins) {
    var handlebarsHelpers = require('handlebars-helpers');
    var handlebarsLayouts = require('handlebars-layouts');

    return function () {
        var prettyfyOptions = JSON.parse(plugins.fs.readFileSync('.prettifyrc'));

        return gulp
            .src(plugins.path.resolve(config.paths.src, 'templates/pages/**/*.hbs'))
            .pipe(plugins.plumber())

            .pipe(plugins.extname())
            .pipe(plugins.frontMatter({"property": "data.page"}))
            .pipe(plugins
                .hb({'cwd': plugins.path.resolve()})
                .data(plugins.path.resolve(config.paths.src, 'templates/data/**/*.json'))
                .data({
                    "assets": "/assets",
                    "cssx": "/css",
                    "jsx": "/js",
                    "timestamp": Date.now()
                })
                .partials(plugins.path.resolve(config.paths.src, 'templates/partials/**/*.hbs'))
                .helpers(plugins.path.resolve(config.paths.tasks, 'html/helpers/*.js'))
                .helpers(handlebarsHelpers)
                .helpers(handlebarsLayouts)
            )
            .pipe(plugins.prettify(prettyfyOptions))
            .pipe(plugins.prod(plugins.htmlmin({'removeComments': true})))
            .pipe(gulp.dest(plugins.path.resolve(config.paths.tmp, 'html')));
    }
};
