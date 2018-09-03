module.exports = function(config, gulp, plugins, options) {
    return function(callback) {
        var name = options.name;

        var files = [
            plugins.path.resolve(config.paths.tmp, 'js/' + name + '.js'),
            plugins.path.resolve(config.paths.tmp, 'js/' + name + '.min.js'),
            plugins.path.resolve(config.paths.tmp, 'js/' + name + '.min.js.map'),
            plugins.path.resolve(config.paths.env, 'build/js/' + name + '.js'),
            plugins.path.resolve(config.paths.env, 'build/js/' + name + '.min.js'),
            plugins.path.resolve(config.paths.env, 'build/js/' + name + '.min.js.map'),
            plugins.path.resolve(config.paths.env, 'dist/js/' + name + '.js'),
            plugins.path.resolve(config.paths.env, 'dist/js/' + name + '.min.js'),
            plugins.path.resolve(config.paths.env, 'dist/js/' + name + '.min.js.map')
        ];

        return plugins.del(files, {'force': true}, callback);
    };
};
