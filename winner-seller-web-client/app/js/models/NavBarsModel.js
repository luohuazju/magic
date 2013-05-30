define([
  'underscore',
  'backbone'
], function( _, Backbone) {

    $.ajaxPrefilter( function( options, originalOptions, jqXHR ) {
        options.url = 'http://localhost:9000/data' + options.url;
    });

    var Items = Backbone.Collection.extend({
        url: '/navbars.JSON',
        parse: function(response) {
            window.logger.debug("getting NavBars from response=" + response);
            return response;
        }
    });

    return Items;
});

