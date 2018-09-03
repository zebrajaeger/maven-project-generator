module.exports = function (config, gulp, plugins) {
    return function (callback) {
        var files = [
            //plugins.path.resolve(config.paths.tmp, 'assets/*'),
            plugins.path.resolve(config.paths.env, 'build/assets/*'),
            plugins.path.resolve(config.paths.env, 'dist/assets/*')
        ];
        return plugins.del(files, {'force': true}, callback);
    };
};
