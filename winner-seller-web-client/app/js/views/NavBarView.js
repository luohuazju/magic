define([
  'jquery',
  'underscore',
  'backbone',
  'models/NavBarsModel',
  'text!templates/NavBarTemplate.html'
], function($, _, Backbone, NavBarsModel, htmlTemplate) {

  var NavBarView = Backbone.View.extend({
    el: $("#navBar"),
    
    initialize: function(){
	},
    
    render: function(){
      window.logger.debug("I am going to hit the Nav Bar Template Page.");
      var widget = this;

      var navBars = new NavBarsModel();

      window.logger.debug(" navBars url = " + navBars.url);
      navBars.fetch({
         data: {},
         type: 'GET',
         success: function(data, response, options) {
             window.logger.debug("NavBarView data = " + data);
             var compiledTemplate = _.template( htmlTemplate, { items : data } );
             $("#navBar").html(compiledTemplate);
         },
         error: function(jqXHR, textStatus, errorThrown) {
            window.logger.error('error arguments: ', arguments);
            window.logger.error(jqXHR + " " + textStatus + " " + errorThrown);
         },
         complete: function(xhr, textStatus) {
            window.logger.debug(textStatus);
         }
      });
    }
  
  });

  return NavBarView;
  
});
