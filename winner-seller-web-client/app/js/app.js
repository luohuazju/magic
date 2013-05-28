
// App.js
// Initialize the app

define([
	'firebase', 
	'jquery', 
	'underscore', 
	'backbone', 
	'backbonefirebase', 
	'bootstrap', 
	'router'
	], function(Firebase, $, _, Backbone, BackboneFirebase, Bootstrap, Router) {

    return {
        initialize: function() {
			console.log("Initializing App!");
		    Router.initialize();
            Backbone.history.start();
        }
    };

});