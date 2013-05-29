define([
  'jquery',
  'underscore',
  'backbone',
  'views/AboutView',
  'views/NavBarView',
  'views/HomeMainView',
  'views/product/ProductsView'
], function($, _, Backbone, AboutView, NavBarView, HomeMainView,ProductsView) {
  
  var Router = Backbone.Router.extend({
    routes: {
      // Define some URL routes
      'productsPlan' : 'productsPlan',
      'about' : 'showAbout',
      '/*' : 'defaultAction'
      //'*actions': 'defaultAction'
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
    
    productsPlan: function(){
    	window.logger.debug("Enterring the productsPlan Page!");
    	new NavBarView().render();
    	new ProductsView().render('PRODUCT_PLAN');
    },
    
    defaultAction: function(){
    	window.logger.debug("Enterring the default Page!");
		new NavBarView().render();
		new HomeMainView().render();
    }
    
  });
  
  return Router;
});
