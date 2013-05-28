
// Main Collection

define([
  'firebase',
  'jquery',
  'underscore',
  'backbone',
  'backbonefirebase',
  'models/main'
], function(Firebase, $, _, Backbone, BackboneFirebase, mainModel){
  var mainCollection = Backbone.Collection.extend({
    model: mainModel,
	url: '/main',

    initialize: function(){
		// Keep this collection in sync with Firebase
		this.backboneFirebase = new BackboneFirebase(this);
    }

  });
 
  return new mainCollection;
});
