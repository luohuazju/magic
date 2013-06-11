define([
  'jquery',
  'underscore',
  'backbone',
  'text!templates/product/ProductEditTemplate.html',
  'bootstrap'
], function($, _, Backbone, htmlTemplate) {

  var View = Backbone.View.extend({
    el: $("#content"),

    initialize: function(){

	},

	events: {
        'submit #saveProduct': 'saveProduct'
    },

    saveProduct: function(ev){
        window.logger.debug("I am about to saving product.");
        var locationDetail = $(ev.currentTarget).serializeObject();

        var location = new Location();
        location.save(locationDetail, {
              success: function (location) {
            	Backbone.history.navigate('locations', {trigger:true});
              },
              error: function(e){
            	console.log("Failed to save on single location" + e);
              }
        });
        return false;
    },

    render: function(type){
      window.logger.debug("I am going to hit the Products Template Page with type = " + type);
      var data = {
  			content: {},
  			_: _
  	  };
      var compiledTemplate = _.template( htmlTemplate, data );
  	  $(this.el).html( compiledTemplate );
    }

  });

  return View;

});
