define([
  'jquery'
], function($) {
	function Config(){
		this.logLevel = 'debug'; //info, debug, all, error
		this.logEnable = 1;   //1=enable, 0=disable

		this.mockServerURL = 'localhost';   //mock server URL
		this.mockServerPort = 9000;                //mock server port

		this.remoteServerURL = 'localhost'; //remote server URL
		this.remoteServerPort = 80;              //remote server port

		this.apiVersion = 'v1'
        this.brandName = 'sillycat'

        //mock,jsonp,json
	    this.navbarsProvider = 'jsonp';
	    this.productsProvider = 'json';
	}
	return Config;
});