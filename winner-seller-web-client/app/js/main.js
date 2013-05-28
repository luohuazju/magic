
// Main.js
// This file contains all of the loading options for require.js

require.config({
    paths: {
		async:  	'libs/require/async',
		firebase:   'https://static.firebase.com/v0/firebase',
        jquery:     'libs/jquery/jquery',
        underscore: 'libs/underscore/underscore',
        backbone:   'libs/backbone/backbone',
		backbonefirebase: 'libs/backbone-firebase/backbone-firebase',	
		bootstrap:  'libs/bootstrap/bootstrap',
        text:       'libs/require/text',
        json2:      'libs/json/json2',
        templates:   '../templates'
    },
	shim: {
		firebase: {
			exports: 'Firebase'
		},
		jquery: {
			exports: '$'
		},
		underscore: {
			exports: '_'
		},
		backbone: {
			deps: ["underscore", "jquery"],
			exports: "Backbone"
		},
		backbonefirebase: {
			deps: ["firebase", "underscore", "backbone"],
			exports: "BackboneFirebase"
		},
		bootstrap: {
			deps: ["jquery"]
		}
	}
});

require([
	'app', 
	'json2'
], function(app) {
    app.initialize();
});