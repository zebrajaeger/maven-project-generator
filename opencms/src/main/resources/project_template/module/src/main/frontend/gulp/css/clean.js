module.exports = function (config, gulp, plugins, options) {
    return function (callback) {
        var name = options.name;
        var files = [
            plugins.path.resolve(config.paths.tmp, 'css/' + name + '.css'),
            plugins.path.resolve(config.paths.tmp, 'css/' + name + '.min.css'),
            plugins.path.resolve(config.paths.tmp, 'css/' + name + '.min.css.map'),
            plugins.path.resolve(config.paths.env, 'build/css/' + name + '.css'),
            plugins.path.resolve(config.paths.env, 'build/css/' + name + '.min.css'),
            plugins.path.resolve(config.paths.env, 'build/css/' + name + '.min.css.map'),
            plugins.path.resolve(config.paths.env, 'dist/css/' + name + '.css'),
            plugins.path.resolve(config.paths.env, 'dist/css/' + name + '.min.css'),
            plugins.path.resolve(config.paths.env, 'dist/css/' + name + '.min.css.map'),
        ];

        return plugins.del(files, {'force': true}, callback);
    };
};
