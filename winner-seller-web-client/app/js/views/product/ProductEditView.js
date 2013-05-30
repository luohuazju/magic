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
