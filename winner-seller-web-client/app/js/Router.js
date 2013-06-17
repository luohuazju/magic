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
      'products/:type' : 'products',
      'product/edit/:productId' : 'product',
      'product/create' : 'product',
      'about' : 'showAbout',
      '' : 'defaultAction'
      //'*actions': 'defaultAction'
    },
  
    initialize: function(){
       //window.logger.debug("init the router, and make backbone start.");
       $.ajaxPrefilter( function( options, originalOptions, jqXHR ) {
          options.crossDomain = true;
          options.xhrFields = { withCredentials: true };
       });
       Backbone.history.start();
    },
    
    showAbout: function(){
    	window.logger.debug("Entering the showAbout Page!");
    	new NavBarView().render();
    	new AboutView().render();
    },
    
    products: function(type){
    	window.logger.debug("Entering the Page products with type=" + type);
    	new NavBarView().render();
    	new ProductsView().render('PRODUCT_PLAN');
    },

    product: function(productId){
        window.logger.debug("Entering the productEdit Page with id=" + productId);
        new NavBarView().render();
        new ProductEditView().render(productId);
    },
    
    defaultAction: function(){
    	window.logger.debug("Entering the default Page!");
		new NavBarView().render();
		new HomeMainView().render();
    }

//    _extractParameters: function(route, fragment) {
//        var result = route.exec(fragment).slice(1);
//        result.unshift(deparam(result[result.length-1]));
//        return result.slice(0,-1);
//    }
    
  });

//  var deparam = function(paramString){
//      var result = {};
//      if( ! paramString){
//          return result;
//      }
//      $.each(paramString.split('&'), function(index, value){
//          if(value){
//              var param = value.split('=');
//              result[param[0]] = param[1];
//          }
//      });
//      return result;
//  };
  
  return Router;
});
