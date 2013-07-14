define([
  'underscore',
  'backbone',
  'Config'
], function( _, Backbone, Config) {
    var item = Backbone.Model.extend({
        urlRoot: '/users',
        parse: function(response) {
            window.logger.debug("getting User from response=" + response);
            return response;
        },
        sync: function(method, model, options){
          options.timeout = 10000;
          var config = new Config();

          alert("4");
          window.logger.info("method = " + method);

          options.xhrFields = { withCredentials: true };

          options.contentType = "application/json; charset=utf-8";
          var url_str = 'http://' + config.remoteServerURL + ':' + config.remoteServerPort
          url_str = url_str + '/' + config.apiVersion + '/' + config.brandName + '/' + 'users';

          options.url = url_str;

          return Backbone.sync(method, model, options);
        }
    });
    return item;
});