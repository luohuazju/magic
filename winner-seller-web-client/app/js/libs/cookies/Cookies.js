define([
  'jquery'
], function($) {

  function CatCookies(){
  }

  //----设置Cookie------
  CatCookies.prototype.setCookie = function(name,value){
       var value = escape(value); //编码
       var nameString = "cat_"+name + "=" + value; //给cookie变量增加前缀
       var extime = new Date();    //返回时间
       extime.setTime (extime.getTime () + 315360000);
       var expiryString = ";expires=" + extime.toGMTString();
       var domainString = "";
       var pathString = ";path=/";
       var setvalue = nameString + expiryString;
       document.cookie = setvalue;
  }

  //---读取Cookie-------
  CatCookies.prototype.getcookie = function(name) {
       var CookieFound = false;
       var start = 0;
       var end = 0;
       var CookieString = document.cookie;

       var i = 0;
       name="cat_"+name; //增加前缀
       while (i <= CookieString.length){
           start = i ;
           end = start + name.length;
           if (CookieString.substring(start, end) == name)
           {
               CookieFound = true;
               break;
           }
           i++;
       }

       if (CookieFound)
       {
           start = end + 1;
           end = CookieString.indexOf(";",start);
           if (end < start)
               end = CookieString.length;
           var getvalue = CookieString.substring(start, end);
           return unescape(getvalue);
       }
       return "";
  }

  return CatCookies;

});