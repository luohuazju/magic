// Filename: views/main/main
define([
	'firebase', 
	'jquery', 
	'underscore', 
	'backbone', 
	'backbonefirebase',
	'collections/main',
	'text!templates/main/main.html'
], function(Firebase, $, _, Backbone, BackboneFirebase, mainCollection, mainMainTemplate) {
	var mainMainView = Backbone.View.extend({
		el: $("#content"),

		initialize: function(){
		},

		render: function(content){
			var data = {
				content: content,
				_: _ 
			};
			var compiledTemplate = _.template( mainMainTemplate, data );
			$(this.el).html( compiledTemplate );
		}
	});
	return new mainMainView;
});