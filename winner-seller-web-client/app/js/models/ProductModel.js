define([
  'underscore',
  'backbone'
], function( _, Backbone) {
    var item = Backbone.Model.extend({
        urlRoot: '/location'
    });
    return item;
});