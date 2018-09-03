module.exports = function (config, gulp, plugins) {
    return function (callback) {
        var files = [
            plugins.path.resolve(config.paths.tmp, 'html/**/*.html'),
            plugins.path.resolve(config.paths.env, 'build/**/*.html')
        ];

        return plugins.del(files, {'force': true}, callback);
    };
};
