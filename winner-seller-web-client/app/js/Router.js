define([
  'jquery',
  'underscore',
  'backbone',
  'views/AboutView',
  'views/NavBarView',
  'views/HomeMainView',
  'views/product/ProductsView',
  'views/product/ProductEditView',
  'views/LoginView',
  'catcookie'
], function($, _, Backbone,
    AboutView,
    NavBarView,
    HomeMainView,
    ProductsView,
    ProductEditView,
    LoginView,
    CatCookie) {

  var navBarView = new NavBarView();
  var aboutView = new AboutView();
  var productsView = new ProductsView();
  var productEditView = new ProductEditView();
  var homeMainView = new HomeMainView();
  var loginView = new LoginView();

  var loginFilter = function(){
     //if cookie not existing, router to login
     var catCookie = new CatCookie("cat");
     if(!catCookie.getcookie("cat_user_name")){
        window.logger.debug("render to login page from router");
        //navBarView.render();
        loginView.render();
        return true;
     }
     return false;
  };

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
        if(loginFilter()) return;
    	window.logger.debug("Entering the showAbout Page!");
    	navBarView.render();
        aboutView.render();
    },
    
    products: function(type){
    	if(loginFilter()) return;
    	window.logger.debug("Entering the Page products with type=" + type);
    	navBarView.render();
        productsView.render(type);
    },

    product: function(productId){
        if(loginFilter()) return;
        window.logger.debug("Entering the productEdit Page with id=" + productId);
        navBarView.render();
        productEditView.render(productId);
    },
    
    defaultAction: function(){
        if(loginFilter()) return;
    	window.logger.debug("Entering the default Page!");
		navBarView.render();
		homeMainView.render();
    }
    
  });


  
  return Router;
});
