define([
  'jquery',
  'underscore',
  'backbone',
  'text!templates/HomeMainTemplate.html'
], function($, _, Backbone, htmlTemplate) {

  var HomeMainView = Backbone.View.extend({
    el: $("#content"),
    
    render: function(){
      window.logger.debug("I am going to render to HomeMainView");
      var data = {
         content: {},
		 _: _ 
	  };
	  var compiledTemplate = _.template( htmlTemplate, data );
	  $(this.el).html( compiledTemplate );
    }
  
  });

  return HomeMainView;
  
});
