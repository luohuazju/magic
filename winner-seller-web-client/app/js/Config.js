define([
  'jquery'
], function($) {
	function Config(){
		this.logLevel = 'debug'; //info, debug, all, error
		this.logEnable = 1;
		this.serverURL = 'http://localhost';
		this.serverPort = 9000;
	}
	return Config;
});