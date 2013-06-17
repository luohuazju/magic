define([
  'jquery',
  'underscore',
  'backbone',
  'text!templates/product/ProductEditTemplate.html',
  'models/ProductModel',
  'bootstrap'
], function($, _, Backbone, htmlTemplate, ProductModel) {

  $.fn.serializeObject = function() {
      var o = {};
      var a = this.serializeArray();
      $.each(a, function() {
          if (o[this.name] !== undefined) {
              if (!o[this.name].push) {
                  o[this.name] = [o[this.name]];
              }
              o[this.name].push(this.value || '');
          } else {
              o[this.name] = this.value || '';
          }
      });
      return o;
    };

  var View = Backbone.View.extend({
    el: $("#content"),

    initialize: function(){

	},

	events: {
        'submit #editForm': 'saveProduct',
        'click #delete': 'deleteProduct'
    },

    deleteProduct: function(ev){
       var itemDetail = $(ev.currentTarget).serializeObject();

       this.item.destroy({
          success: function(){
              window.logger.debug("delete product hitting.");
              Backbone.history.navigate('products/productplan', {trigger:true});
          }
       });
    },

    saveProduct: function(ev){
        window.logger.debug("I am about to saving product.");
        var itemDetail = $(ev.currentTarget).serializeObject();

        var newItem = new ProductModel();


        newItem.save(itemDetail, {
              success: function (data) {
                console.log("success to get the data back = " + data);
            	Backbone.history.navigate('products/productplan', {trigger:true});
              },
              error: function(e){
            	console.log("Failed to save on product" + e);
              }
        });

        return false;
    },

    render: function(productId){
      var that = this;
      if(productId){
        that.item = new ProductModel({id: productId});
        that.item.fetch({
           success: function(data){
              window.logger.debug("I am getting data=" + data.id);
              var compiledTemplate = _.template( htmlTemplate, {item:data} );
              $("#content").html( compiledTemplate );
           },
           error: function(e){
                console.log("Failed to fetch the product" + e);
           }
        });
      }else{
        var compiledTemplate = _.template( htmlTemplate, {item:null} );
  	    $(this.el).html( compiledTemplate );
      }
    }

  });

  return View;

});
