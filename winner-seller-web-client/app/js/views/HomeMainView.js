define([
  'jquery',
  'underscore',
  'backbone',
  'text!templates/HomeMainTemplate.html',
  'catcookie'
], function($, _, Backbone, htmlTemplate, CatCookie) {

  var HomeMainView = Backbone.View.extend({
    el: $("#content"),
    
    initialize: function(){
    	
	},
    
    render: function(){
      //var catCookie = new CatCookie("cat");

      //if(catCookie.getcookie("cat_user_name")){
         window.logger.debug("render to home page");

         var data = {
                  content: {},
         		 _: _
         };
         var compiledTemplate = _.template( htmlTemplate, data );
         $(this.el).html( compiledTemplate );
      //}else{
      //   window.logger.debug("render to login page");
      //}
    }
  
  });

  return HomeMainView;
  
});
