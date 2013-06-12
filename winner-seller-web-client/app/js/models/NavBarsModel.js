define([
  'underscore',
  'backbone',
  'Config'
], function( _, Backbone, Config) {

    var Items = Backbone.Collection.extend({
        url: '/navbars',
        parse: function(response) {
            //window.logger.debug("getting NavBars from response=" + response);
            return response;
        },
        sync: function(method, model, options){
           options.timeout = 10000;

           var config = new Config(); //loading configuration
           //json mock
           if(config.navbarsProvider == 'mock'){
              options.url = "http://" + config.mockServerURL + ':' + config.mockServerPort + '/data/' + "navbars" + ".JSON";
              options.dataType = 'json';
           }

           //jsonp
           if(config.navbarsProvider == 'jsonp'){
              options.dataType = "jsonp";
              options.crossDomain = true;
              var url_str = 'http://' + 'customer' + ':' + 'customer' + '@';
              url_str = url_str + config.remoteServerURL +':' + config.remoteServerPort;
              url_str = url_str + '/' + config.apiVersion +'/' + config.brandName + '/' + 'navbars';
              options.url = url_str;
           }

           return Backbone.sync(method, model, options);
        }
    });

    return Items;
});

