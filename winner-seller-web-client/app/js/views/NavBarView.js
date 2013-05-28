define([
  'jquery',
  'underscore',
  'backbone',
  'text!templates/NavBarTemplate.html'
], function($, _, Backbone, htmlTemplate) {

  var NavBarView = Backbone.View.extend({
    el: $("#navBar"),
    
    initialize: function(){
	},
    
    render: function(){
      window.logger.debug("I am going to hit the Nav Bar Template Page.");
      var data = {
			navbar: {},
			_: _ 
	  };
      var compiledTemplate = _.template( htmlTemplate, data );
	  $(this.el).html( compiledTemplate );
    }
  
  });

  return NavBarView;
  
});
