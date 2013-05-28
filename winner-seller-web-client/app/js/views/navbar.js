
// Filename: views/navbar
// This is the navigation bar

define([
	'firebase', 
	'jquery', 
	'underscore', 
	'backbone', 
	'backbonefirebase',
	'text!templates/navbar.html'
], function(Firebase, $, _, Backbone, BackboneFirebase, navbarTemplate) {
	var navbarView = Backbone.View.extend({
		el: $("#navBar"),

		initialize: function(){
		},

		render: function(navbar){
			var data = {
				navbar: navbar,
				_: _ 
			};
			var compiledTemplate = _.template( navbarTemplate, data );
			$(this.el).html( compiledTemplate );
		}
	});
	return new navbarView;
});