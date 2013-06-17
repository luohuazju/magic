define([
  'underscore',
  'backbone',
  'Config'
], function( _, Backbone, Config) {

    var Items = Backbone.Collection.extend({
        url: '/products',
        parse: function(response) {
            //window.logger.debug("getting Products from response=" + response);
            return response;
        },
        sync: function(method, model, options){
           options.timeout = 10000;

           var config = new Config(); //loading configuration
           //json mock
           if(config.productsProvider == 'mock'){
              options.url = "http://" + config.mockServerURL + ':' + config.mockServerPort + '/data/' + "products" + ".JSON";
              options.dataType = 'json';
           }

           //json
           if(config.productsProvider == 'json'){
              options.dataType = "json";
              options.crossDomain = true;
              options.xhrFields = { withCredentials: true };

              //options.beforeSend = function (xhr) {
              //   xhr.setRequestHeader('Authorization', 'Basic Y3VzdG9tZXI6Y3VzdG9tZXI=');
              //};

              var url_str = 'http://' + config.remoteServerURL + ':' + config.remoteServerPort
              url_str = url_str + '/' + config.apiVersion + '/' + config.brandName + '/' + 'products';
              options.url = url_str;
           }

           return Backbone.sync(method, model, options);
        }
    });

    return Items;
});

