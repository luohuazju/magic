define([
  'jquery',
  'underscore',
  'backbone',
  'text!templates/about/AboutTemplate.html'
], function($, _, Backbone, htmlTemplate) {

  var AboutView = Backbone.View.extend({
    el: $("#content"),
    
    initialize: function(){
    	
	},
    
    render: function(){
      window.logger.debug("I am going to hit the About Template Page.");
      var data = {
  			content: {},
  			_: _ 
  	  };
      var compiledTemplate = _.template( htmlTemplate, data );
  	  $(this.el).html( compiledTemplate );
    }
  
  });

  return AboutView;
  
});
