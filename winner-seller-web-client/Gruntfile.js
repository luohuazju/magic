'use strict';

var lrSnippet = require('grunt-contrib-livereload/lib/utils').livereloadSnippet;

var mountFolder = function (connect, dir) {
	  return connect.static(require('path').resolve(dir));
};

module.exports = function (grunt) {
	  // load all grunt tasks
	require('matchdep').filterDev('grunt-*').forEach(grunt.loadNpmTasks);
	
	grunt.initConfig({
	    server: {
	      port: 8000,
	      base: '.'
	    },

    });
	
	grunt.registerTask('default', 'server');
};