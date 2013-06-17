define([
  'underscore',
  'backbone',
  'Config'
], function( _, Backbone, Config) {
    var item = Backbone.Model.extend({
        urlRoot: '/products',
        parse: function(response) {
            window.logger.debug("getting Product from response=" + response);
            return response;
        },
        sync: function(method, model, options){
           options.timeout = 10000;
           var config = new Config();

              console.log("method = " + method);
              console.log("model = " + model.productName);

              //options.data = '{"productName" : "iphone 100","productDesn" : "A good mobile device.","createDate" : "2012-05-22 13:33","expirationDate" : "2012-05-22 14:33","productCode" : "IPHONE5"}';
              //options.data = model;


              options.xhrFields = { withCredentials: true };

              options.contentType = "application/json; charset=utf-8";
              //options.url = "http://localhost/v1/sillycat/products";
              var url_str = 'http://' + config.remoteServerURL + ':' + config.remoteServerPort
              url_str = url_str + '/' + config.apiVersion + '/' + config.brandName + '/' + 'products';
              options.url = url_str;

           return Backbone.sync(method, model, options);
        }
    });
    return item;
});