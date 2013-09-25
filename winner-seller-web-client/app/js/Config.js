define([
  'jquery'
], function($) {
	function Config(){
		this.logLevel = 'debug'; //info, debug, all, error
		this.logEnable = 1;   //1=enable, 0=disable

		this.mockServerURL = 'localhost';   //mock server URL
		this.mockServerPort = 9000;                //mock server port

		this.remoteServerURL = 'seller.api.sillycat.com'; //remote server URL
		this.remoteServerPort = 9002;              //remote server port

		this.apiVersion = 'v1'

        //mock,jsonp,json
	    this.navbarsProvider = 'json';
	    this.productsProvider = 'json';
	    this.usersProvider = 'json';
	}
	return Config;
});