define([
  'underscore',
  'backbone'
], function( _, Backbone) {

    var Items = Backbone.Collection.extend({
        url: '/navbars',
        parse: function(response) {
            window.logger.debug("getting NavBars from response=" + response);
            return response;
        },
        sync: function(method, model, options){
           options.timeout = 10000;

//           options.url = 'http://localhost:9000/data' + "/navbars" + ".JSON";
//           options.dataType = 'json';

           options.dataType = "jsonp";
           options.crossDomain = true;
           options.url = 'http://localhost:9002/v1/sillycat' + "/navbars";

           return Backbone.sync(method, model, options);
        }
    });

    return Items;
});

