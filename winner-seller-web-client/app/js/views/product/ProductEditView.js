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
        'submit #editForm': 'saveProduct'
    },

    saveProduct: function(ev){
        window.logger.debug("I am about to saving product.");
        var itemDetail = $(ev.currentTarget).serializeObject();

        var item = new ProductModel();

//        Backbone.ajax({
//            xhrFields: {
//               withCredentials: true
//            },
//            url: "http://localhost/v1/sillycat/products",
//            type: 'POST',
//            contentType: 'application/json; charset=utf-8',
//            data: '{"productName" : "iphone 6","productDesn" : "A good mobile device.","createDate" : "2012-05-22 13:33","expirationDate" : "2012-05-22 14:33","productCode" : "IPHONE5"}',
//            dataType: 'json',
//            beforeSend: function(xhr){
//                xhr.setRequestHeader("Authorization","Basic Y3VzdG9tZXI6Y3VzdG9tZXI=");
//            },
//            success: function(responseData, textStatus, jqXHR) {
//               var value = responseData;
//                alert(value);
//            },
//            error: function (responseData, textStatus, errorThrown) {
//                    alert('POST failed.' + textStatus);
//            }
//        });

//         item.productName="iphone 100";
//         item.productDesn="A good mobile device.";
//         item.createDate="2012-05-22 13:33";
//         item.expirationDate="2012-05-22 14:33";
//         item.productCode="IPHONE5";

        item.save(itemDetail, {
              success: function (data) {
                console.log("success to get the data back = " + data);
            	Backbone.history.navigate('productsPlan', {trigger:true});
              },
              error: function(e){
            	console.log("Failed to save on product" + e);
              }
        });

        return false;
    },

    render: function(type){
      window.logger.debug("I am going to hit the Products Template Page with type = " + type);
      var data = {
  			content: {},
  			_: _
  	  };
      var compiledTemplate = _.template( htmlTemplate, data );
  	  $(this.el).html( compiledTemplate );
    }

  });

  return View;

});
