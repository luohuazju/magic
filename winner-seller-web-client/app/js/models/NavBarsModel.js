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

           //json
           if(config.navbarsProvider == 'json'){
              options.dataType = "json";
              options.crossDomain = true;
              var url_str = 'http://';
              url_str = url_str + config.remoteServerURL +':' + config.remoteServerPort;
              url_str = url_str + '/' + config.apiVersion + '/' + 'navbars';
              options.url = url_str;

              options.beforeSend = function (xhr) {
                //Authorization       Authentication
                //window.btoa  window.atob
                var encryption_str = window.btoa("customer@gmail.com:customer");
                encryption_str = "Basic " + encryption_str
                xhr.setRequestHeader("Authorization", encryption_str );
              };
           }

           return Backbone.sync(method, model, options);
        }
    });

    return Items;
});

