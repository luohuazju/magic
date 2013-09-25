define([
  'underscore',
  'backbone',
  'Config'
], function( _, Backbone, Config) {
    var item = Backbone.Model.extend({
        urlRoot: '/auth',
        parse: function(response) {
            window.logger.debug("getting User from response=" + response);
            return response;
        },
        sync: function(method, model, options){
          options.timeout = 10000;
          var config = new Config();

          window.logger.info("method = " + method);

          options.xhrFields = { withCredentials: true };

          options.beforeSend = function (xhr) {
            //Authorization       Authentication
            //window.btoa  window.atob
            var encryption_str = window.btoa("customer@gmail.com:customer");
            encryption_str = "Basic " + encryption_str
            xhr.setRequestHeader("Authorization", encryption_str );
          };

          options.contentType = "application/json; charset=utf-8";
          var url_str = 'http://' + config.remoteServerURL + ':' + config.remoteServerPort
          url_str = url_str + '/' + config.apiVersion + '/' + 'auth';

          options.url = url_str;

          return Backbone.sync(method, model, options);
        }
    });
    return item;
});