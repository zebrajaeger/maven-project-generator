#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
'use strict';

/*
* VARIABLES + CONSTANTS
* */
const gulp = require('gulp');
const plugins = require('gulp-load-plugins')();

plugins.log = require('fancy-log');
plugins.sassGlob = plugins.sassGlob2;
plugins.fs = require('fs');
plugins.del = require('del');
plugins.path = require('path');
plugins.fileExists = require('file-exists');
plugins.eventStream = require('event-stream');
plugins.runSequence = require('run-sequence');
plugins.browserSync = require('browser-sync').create();
plugins.beep = require('beepbeep');

plugins.dev = plugins.environments.development;
plugins.prod = plugins.environments.production;

var config = require('./.project.json');

plugins.getModule = function (task, options) {
    return require(plugins.path.resolve(config.paths.tasks, task))(config, gulp, plugins, options);
};

// CLEANUP
gulp.task('app-css-clean', plugins.getModule('css/clean', {name: 'app'}));
gulp.task('vendor-css-clean', plugins.getModule('css/clean', {name: 'vendor'}));
gulp.task('app-js-clean', plugins.getModule('js/clean', {name: 'app'}));
gulp.task('vendor-js-clean', plugins.getModule('js/clean', {name: 'vendor'}));
gulp.task('html-clean', plugins.getModule('html/clean'));
gulp.task('assets-clean', plugins.getModule('assets/clean'));

// COMPILING
gulp.task('app-css-compile', plugins.getModule('css/app'));
gulp.task('app-js-compile', plugins.getModule('js/compile', {name: 'app'}));
gulp.task('vendor-css-compile', plugins.getModule('css/vendor'));
gulp.task('vendor-js-compile', plugins.getModule('js/compile', {name: 'vendor'}));
gulp.task('html-compile', plugins.getModule('html/compile'));

// COPYING
gulp.task('app-css-copy', plugins.getModule('css/copy', {name: 'app'}));
gulp.task('vendor-css-copy', plugins.getModule('css/copy', {name: 'vendor'}));
gulp.task('app-js-copy', plugins.getModule('js/copy', {name: 'app'}));
gulp.task('vendor-js-copy', plugins.getModule('js/copy', {name: 'vendor'}));
gulp.task('html-copy', plugins.getModule('html/copy'));
gulp.task('assets-copy', plugins.getModule('assets/copy'));

gulp.task('beep', function () {
    plugins.beep();
});

/*
* MAIN TASKS
* */
gulp.task('build-js', function (callback) {
    plugins.runSequence(
        ['app-js-clean', 'vendor-js-clean'],
        ['app-js-compile', 'vendor-js-compile'],
        ['app-js-copy', 'vendor-js-copy'],
        callback
    );
});

gulp.task('build-css', function (callback) {
    plugins.runSequence(
        ['app-css-clean', 'vendor-css-clean'],
        ['app-css-compile', 'vendor-css-compile'],
        ['app-css-copy', 'vendor-css-copy'],
        callback
    );
});

gulp.task('build-html', function (callback) {
    plugins.runSequence(
        'html-clean',
        'html-compile',
        'html-copy',
        callback
    );
});

gulp.task('build-assets', function (callback) {
    plugins.runSequence(
        'assets-clean',
        'assets-copy',
        callback
    );
});

gulp.task('build', function (callback) {
    plugins.runSequence(
        ['app-js-clean', 'vendor-js-clean', 'app-css-clean', 'vendor-css-clean', 'html-clean', 'assets-clean'],
        ['app-js-compile', 'vendor-js-compile', 'app-css-compile', 'vendor-css-compile', 'html-compile'],
        ['app-js-copy', 'vendor-js-copy', 'app-css-copy', 'vendor-css-copy', 'html-copy', 'assets-copy'],
        'beep',
        callback
    )
});

gulp.task('bs-reload', function () {
    plugins.browserSync.reload();
});

gulp.task('serve', ['build'], function () {

    plugins.browserSync.init({
        proxy: "localhost:8080",
        serveStatic: [
            {dir: '../../../target/build'},
            {dir: '../../../target/build', route: '/system/modules/${rootArtifactId}.basis/resources'}
        ],
        ui: {
            port: 3001
        }
    });

    plugins.browserSync.init({
        server: '../../../target/build'
    });

    gulp.watch('src/styles/app/**/*.scss', function () {
        plugins.runSequence('app-css-clean', 'app-css-compile', 'app-css-copy', 'bs-reload', 'beep');
    });

    gulp.watch('src/styles/vendor/**/*.scss', function () {
        plugins.runSequence('vendor-css-clean', 'vendor-css-compile', 'vendor-css-copy', 'bs-reload', 'beep');
    });

    gulp.watch('src/js/app/**/*.js', function () {
        plugins.runSequence('app-js-clean', 'app-js-compile', 'app-js-copy', 'bs-reload', 'beep');
    });

    gulp.watch('src/js/vendor/**/*.js', function () {
        plugins.runSequence('vendor-js-clean', 'vendor-js-compile', 'vendor-js-copy', 'bs-reload', 'beep');
    });

    gulp.watch(['src/templates/**/*.hbs', 'src/templates/data/**/*.json'], function () {
        plugins.runSequence('html-clean', 'html-compile', 'html-copy', 'bs-reload', 'beep');
    });

    gulp.watch('src/assets/**/*', function () {
        plugins.runSequence('assets-clean', 'assets-copy', 'bs-reload', 'beep');
    });
});

gulp.task('default', ['serve']);
