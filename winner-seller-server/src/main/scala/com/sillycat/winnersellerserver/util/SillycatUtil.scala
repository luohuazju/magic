package com.sillycat.winnersellerserver.util

import com.sillycat.winnersellerserver.CrossDomainHeaders.`Access-Control-Allow-Origin`
import spray.http.HttpHeader
import com.typesafe.config.ConfigFactory
import spray.http.HttpHeaders._
import spray.http.HttpHeaders.RawHeader

/**
 * Created with IntelliJ IDEA.
 * User: carl
 * Date: 6/12/13
 * Time: 3:07 PM
 * To change this template use File | Settings | File Templates.
 */
object SillycatUtil {
  //def getCrossDomainHeaders(originHeader: Option[String]): List[RawHeader] = {
  def getCrossDomainHeaders(originHeader: String): List[RawHeader] = {
    val headers = List(RawHeader("Access-Control-Max-Age", "86400"),RawHeader("Access-Control-Allow-Credentials","true"))
    val config = ConfigFactory.load()
    if(config.getBoolean("server.crossdomain.eanble") == true){
      val lists = config.getStringList("server.crossdomain.list")
      if(lists.contains(originHeader)){
         headers
           .::(RawHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS"))
           .::(RawHeader("Access-Control-Allow-Headers", "accept, origin, authorization, content-type, X-Requested-With, X-HTTP-Method-Override"))
           .::(RawHeader("Access-Control-Allow-Origin", originHeader ))
      }else{
         headers.::(RawHeader("Access-Control-Allow-Origin", ""))
      }
    }else{
      headers
    }
  }
}
