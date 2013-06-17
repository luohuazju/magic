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

              window.logger.info("method = " + method);


              options.xhrFields = { withCredentials: true };

              options.contentType = "application/json; charset=utf-8";
              var url_str = 'http://' + config.remoteServerURL + ':' + config.remoteServerPort
              url_str = url_str + '/' + config.apiVersion + '/' + config.brandName + '/' + 'products';
              if(model.id){
                url_str = url_str + '/' + model.id;
              }
              options.url = url_str;

           return Backbone.sync(method, model, options);
        }
    });
    return item;
});