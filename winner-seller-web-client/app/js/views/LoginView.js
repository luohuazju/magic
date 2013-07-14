define([
  'jquery',
  'underscore',
  'backbone',
  'text!templates/LoginTemplate.html'
], function($, _, Backbone, htmlTemplate) {

    var view = Backbone.View.extend({
        el: $("#content"),

        initialize: function(){

    	},

        render: function(){
          window.logger.debug("render to login page");

          var data = {
                      content: {},
             		 _: _
          };
          var compiledTemplate = _.template( htmlTemplate, data );
          $(this.el).html( compiledTemplate );

        }

    });

    return view;

});