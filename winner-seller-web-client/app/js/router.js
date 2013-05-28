define([
	// Define the libs
	'firebase', 
	'jquery', 
	'underscore', 
	'backbone', 
	'backbonefirebase',
	// Define the views
	'views/navbar',
	'views/main/main',
	// Define the collections
 	'collections/main'
], function(Firebase, $, _, Backbone, BackboneFirebase, navbarView, mainMainView, mainCollection) {
	
	var Router = Backbone.Router.extend({
        routes: {
			'about': 'about',
			'*actions': 'defaultAction'
		},
	
		about: function(){
	
		},
	
	    defaultAction: function(actions){
			navbarView.render();
			mainMainView.render();
		}
		
	});

	return {
		initialize: function() {
			new Router();
		}
	};

});