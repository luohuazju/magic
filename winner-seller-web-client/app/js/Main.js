require.config({
	waitSeconds : 120, //make sure it is enough to load all scripts
    paths: {
      jquery: '//cdnjs.cloudflare.com/ajax/libs/jquery/1.8.2/jquery',
      underscore: '//cdnjs.cloudflare.com/ajax/libs/underscore.js/1.4.1/underscore',
      backbone: '//cdnjs.cloudflare.com/ajax/libs/backbone.js/0.9.2/backbone-min',
      //backbone: '//cdnjs.cloudflare.com/ajax/libs/backbone.js/1.0.0/backbone',
      text: '//cdnjs.cloudflare.com/ajax/libs/require-text/2.0.5/text',
      async: 'libs/require/async',
      log4javascript: '//cdnjs.cloudflare.com/ajax/libs/log4javascript/1.4.3/log4javascript',
      json2: '//cdnjs.cloudflare.com/ajax/libs/json2/20121008/json2',
      log4javascript_custom: 'libs/log4javascript/log4javascript_custom',
      templates:   '../templates',
	  bootstrap:  '../libs/bootstrap/2.3.2/js/bootstrap'
    },
	shim: {
		jquery: {
			exports: '$'
		},
		underscore: {
			exports: '_'
		},
		backbone: {
			deps: ["underscore", "jquery"],
			exports: "Backbone"
		},
		bootstrap: {
			deps: ["jquery"]
		}
	}
});

require([
    'jquery',
	'Router',
	'Config',
	'Log4j',
	'json2',
	'bootstrap'
], function($, Router, Config, Log4j){
   	
	new Log4j().init();
   	
   	var IndexPageView = Backbone.View.extend({
   		el: "#content",
	router: null,
	
	initialize: function(){
		//window.logger.debug("initialize the IndexPageView and whole system.", "Good Luck.");
		window.logger.info("Config Start==================================");
		var config = new Config();
		for (var c in config) if (config.hasOwnProperty(c)) {
          window.logger.info(c + " = " + config[c]);
        }
		window.logger.info("Config End====================================");
   			this.router = new Router();
   		}
   	});
   	
   	new IndexPageView();
   	
});