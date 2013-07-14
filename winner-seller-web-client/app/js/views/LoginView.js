define([
  'jquery',
  'underscore',
  'backbone',
  'text!templates/LoginTemplate.html',
  'models/UserModel',
  'catcookie'
], function($, _, Backbone, htmlTemplate, UserModel, CatCookie) {

    var view = Backbone.View.extend({
        el: $("#content"),

        initialize: function(){

    	},

    	events: {
            //'click #logonSystem': 'logonSystem'
            'submit #userLoginForm': 'logonSystem',
        },

        logonSystem: function(ev){
            var userDetail = $(ev.currentTarget).serializeObject();
            window.logger.debug("logonSystem email=" + userDetail.email + " password=" + userDetail.password);

//            newItem.save({},{
//                success: function (data) {
//                   alert("2");
//                   window.logger.debug("I am getting data from logon=" + data.id);
//                   var catCookie = new CatCookie("cat");
//                   catCookie.setCookie("cat_user_name", userDetail.email);
//                   Backbone.history.navigate('', {trigger:true});
//                   return false;
//                },
//                error: function(e){
//                   return false;
//                }
//            });

            return false;
//            that.item.save({
//                success: function(data){
//                    window.logger.debug("I am getting data from logon=" + data.id);
//                    var catCookie = new CatCookie("cat");
//                    catCookie.setCookie("cat_user_name", userDetail.email);
//                    Backbone.history.navigate('', {trigger:true});
//                },
//                error: function(e){
//                    console.log("Failed to fetch the User" + e);
//                    Backbone.history.navigate('logon', {trigger:true});
//                }
//            });
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