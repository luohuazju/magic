define([
  'jquery',
  'underscore',
  'backbone',
  'views/ProductView',
  'views/AboutView',
  'views/NavBarView',
  'views/HomeMainView'
], function($, _, Backbone, ProductView, AboutView, NavBarView, HomeMainView) {
  
  var Router = Backbone.Router.extend({
    routes: {
      // Define some URL routes
      'products' : 'showProduct',
      'about' : 'showAbout',
      '*actions': 'defaultAction'
    },
  
    initialize: function(){
       window.logger.debug("init the router, and make backbone start.");
       Backbone.history.start();
    },
    
    showAbout: function(){
    	window.logger.debug("Entering the showAbout Page!");
    	new NavBarView().render();
    	new AboutView().render();
    },
    
    showProduct: function(){
    	window.logger.debug("Enterring the showProduct Page!");
    	new NavBarView().render();
    	new ProductView().render();
    },
    
    defaultAction: function(){
    	window.logger.debug("Enterring the default Page!");
		new NavBarView().render();
		new HomeMainView().render();
    }
    
  });
  
  return Router;
});
