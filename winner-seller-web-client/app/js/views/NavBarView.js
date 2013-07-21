define([
  'jquery',
  'underscore',
  'backbone',
  'models/NavBarsModel',
  'text!templates/NavBarTemplate.html',
  'catcookie'
], function($, _, Backbone, NavBarsModel, htmlTemplate, CatCookie) {

  var NavBarView = Backbone.View.extend({
    el: $("#navBar"),
    
    initialize: function(){
	},

	events: {
       'click #logout': 'logoutSystem'
    },

    logoutSystem: function(ev){
       window.logger.debug("Logout the system!");
       var catCookie = new CatCookie("cat");
       catCookie.setCookie("cat_user_name", "");
       Backbone.history.navigate('logon', {trigger:true} );
    },
    
    render: function(){
      //window.logger.debug("I am going to hit the Nav Bar Template Page.");
      var widget = this;

      var models = new NavBarsModel();

      //window.logger.debug(" navBars url = " + models.url);
      models.fetch({
         data: {},
         type: 'GET',
         success: function(data, response, options) {
             window.logger.debug("response data = " + data);
             var compiledTemplate = _.template( htmlTemplate, { items : data } );
             $("#navBar").html(compiledTemplate);
         },
         error: function(jqXHR, textStatus, errorThrown) {
            window.logger.error('error arguments: ', arguments);
            window.logger.error(jqXHR + " " + textStatus + " " + errorThrown);
         },
         complete: function(xhr, textStatus) {
            //window.logger.debug(textStatus);
         }
      });
    }
  
  });

  return NavBarView;
  
});
