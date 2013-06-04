define([
  'jquery',
  'underscore',
  'backbone',
  'views/AboutView',
  'views/NavBarView',
  'views/HomeMainView',
  'views/product/ProductsView',
  'views/product/ProductEditView'
], function($, _, Backbone, AboutView, NavBarView, HomeMainView, ProductsView, ProductEditView) {
  
  var Router = Backbone.Router.extend({
    routes: {
      // Define some URL routes
      'productsPlan' : 'productsPlan',
      'productEdit' : 'productEdit',
      'about' : 'showAbout',
      '' : 'defaultAction'
      //'*actions': 'defaultAction'
    },
  
    initialize: function(){
       window.logger.debug("init the router, and make backbone start.");
       $.ajaxPrefilter( function( options, originalOptions, jqXHR ) {
              // Modify options, control originalOptions, store jqXHR, etc
              //options.url = 'http://localhost:9000/data' + options.url + ".JSON";
              //options.dataType = 'json';

              //options.url = 'http://localhost:9002/v1/sillycat' + options.url;
       });
       Backbone.history.start();
    },
    
    showAbout: function(){
    	window.logger.debug("Entering the showAbout Page!");
    	new NavBarView().render();
    	new AboutView().render();
    },
    
    productsPlan: function(){
    	window.logger.debug("Entering the productsPlan Page!");
    	new NavBarView().render();
    	new ProductsView().render('PRODUCT_PLAN');
    },

    productEdit: function(){
        window.logger.debug("Entering the productEdit Page!");
        new NavBarView().render();
        new ProductEditView().render();
    },
    
    defaultAction: function(){
    	window.logger.debug("Entering the default Page!");
		new NavBarView().render();
		new HomeMainView().render();
    }
    
  });
  
  return Router;
});
