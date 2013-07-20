define([
  'jquery',
  'underscore',
  'backbone',
  'text!templates/LoginTemplate.html',
  'models/AuthModel',
  'catcookie'
], function($, _, Backbone, htmlTemplate, AuthModel, CatCookie) {

    var view = Backbone.View.extend({
        el: $("#content"),

        initialize: function(){

    	},

    	events: {
            //'click #logonSystem': 'logonSystem'
            'submit #userLoginForm': 'logonSystem',
        },

        logonSystem: function(ev){
            var that = this
            var userDetail = $(ev.currentTarget).serializeObject();
            window.logger.debug("logonSystem email=" + userDetail.email + " password=" + userDetail.password);

            var newItem = new AuthModel();


            newItem.save(userDetail, {
                success: function (data) {
                      window.logger.debug("I am getting data=" + data.id + " email=" + data.get("email")
                        + " password=" + data.get("password"));
                      if(data.id){
                          window.logger.debug("Logon successfully.");
                          var catCookie = new CatCookie("cat");
                          catCookie.setCookie("cat_user_name", data.get("email"));
                          Backbone.history.navigate('home', {trigger:true});
                      }
//                      else{
//                          window.logger.error("Wrong user name and password.");
//                          Backbone.history.navigate('logon?error=true', {trigger:true});
//                      }
                },
                error: function(model, response) {
                   console.error("Failed to fetch the status=" + response.responseText + " " + response.status
                    + " " + response.statusText);
                   Backbone.history.navigate('logon?error=true', {trigger:true});
                }
//                error: function(e){
//                  console.log("Failed to fetch the user" + e.status);
//                  Backbone.history.navigate('logon?error=true', {trigger:true});
//                }
            });

            return false;
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