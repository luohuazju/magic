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
       this.item.destroy({
          success: function(){
              window.logger.debug("delete product hitting.");
              Backbone.history.navigate('products/productplan', {trigger:true});
          }
       });
       this.item = null;
    },

    saveProduct: function(ev){
        //window.logger.debug("I am about to saving product.");
        var itemDetail = $(ev.currentTarget).serializeObject();

        itemDetail.productPriceCN = parseFloat(itemDetail.productPriceCN);
        itemDetail.productPriceUS = parseFloat(itemDetail.productPriceUS);
        itemDetail.productSellingPriceCN = parseFloat(itemDetail.productSellingPriceCN);
        itemDetail.productWeight = parseFloat(itemDetail.productWeight);
        itemDetail.productWin = parseFloat(itemDetail.productWin);

        var newItem = new ProductModel();

        if(this.item){
          window.logger.info("I am going to update the item.");
          this.item.save(itemDetail,{
            success: function (data) {
              //window.logger.debug("success to get the data back = " + data);
              Backbone.history.navigate('products/' + item.productType, {trigger:true});
            },
            error: function(e){
              window.logger.error("Failed to save on product" + e);
            }
          });
          this.item = null;
        }else{
          window.logger.info("I am going to create the item.")
          newItem.save(itemDetail, {
            success: function (data) {
              //window.logger.debug("success to get the data back = " + data);
              Backbone.history.navigate('products/' + itemDetail.productType, {trigger:true});
            },
            error: function(e){
              window.logger.error("Failed to save on product" + e);
            }
          });
          this.item = null;
        }

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
