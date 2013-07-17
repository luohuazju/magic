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
            var that = this
            var userDetail = $(ev.currentTarget).serializeObject();
            window.logger.debug("logonSystem email=" + userDetail.email + " password=" + userDetail.password);

            var newItem = new UserModel();


            newItem.save(userDetail, {
                        success: function (data) {
                          window.logger.debug("success to get the data back = " + data);
                          Backbone.history.navigate('products/' + itemDetail.productType, {trigger:true});
                        },
                        error: function(e){
                          window.logger.error("Failed to save on product" + e);
                        }
            });

//            that.item.fetch({
//                       success: function(data){
//                          window.logger.debug("I am getting data=" + data.id);
//                          if(userDetail.email != ""){
//                              Backbone.history.navigate('', {trigger:true});
//                          }else{
//                              Backbone.history.navigate('logon?error=true', {trigger:true});
//                          }
//                       },
//                       error: function(e){
//                            console.log("Failed to fetch the user" + e);
//                            Backbone.history.navigate('logon?error=true', {trigger:true});
//                       }
//            });
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