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
              window.logger.debug("I am going to send the ajax call, method=" + method);
              options.dataType = "json";
              options.crossDomain = true;

              options.xhrFields = { withCredentials: true };

              options.beforeSend = function (xhr) {
                  //Authorization       Authentication
                  //window.btoa
                  //window.atob                 Y3VzdG9tZXJAZ21haWwuY29tOmN1c3RvbWVy
                  var encryption_str = window.btoa("customer@gmail.com:customer");
                  encryption_str = "Basic " + encryption_str
                  window.logger.debug("The encryption string is " + encryption_str);
                  //xhr.setRequestHeader("Authorization", "Basic Y3VzdG9tZXI6Y3VzdG9tZXI=");
                  xhr.setRequestHeader("Authorization", encryption_str );
              };

              var url_str = 'http://' + config.remoteServerURL + ':' + config.remoteServerPort;
              url_str = url_str + '/' + config.apiVersion + '/' + config.brandName + '/' + 'products';

              options.url = url_str;
           }

           return Backbone.sync(method, model, options);
        }
    });

    return Items;
});

