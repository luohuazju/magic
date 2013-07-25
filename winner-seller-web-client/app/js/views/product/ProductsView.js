define([
  'jquery',
  'underscore',
  'backbone',
  'models/ProductsModel',
  'text!templates/product/ProductsTemplate.html',
  'bootstrap'
], function($, _, Backbone, ProductsModel, htmlTemplate) {

  var View = Backbone.View.extend({
    el: $("#content"),
    
    initialize: function(){
    	
	},
    
    render: function(type){
      window.logger.debug("I am going to hit the Products Template Page with type = " + type);
      var models = new ProductsModel();

       window.logger.debug(" products url = " + models.url);
       models.fetch({
           data: { productType: type },
           type: 'GET',
           success: function(data, response, options) {
               window.logger.debug("response data = " + data);
               var compiledTemplate = _.template( htmlTemplate, { items : data } );
               $("#content").html(compiledTemplate);
           },
           //username: "customer@gmail.com",
           //password: "customer",
           error: function(jqXHR, textStatus, errorThrown) {
              window.logger.error('error arguments: ', arguments);
              window.logger.error(jqXHR + " " + textStatus + " " + errorThrown);
           },
           complete: function(xhr, textStatus) {
              window.logger.debug(textStatus);
           }
       });
    }
  
  });

  return View;
  
});
