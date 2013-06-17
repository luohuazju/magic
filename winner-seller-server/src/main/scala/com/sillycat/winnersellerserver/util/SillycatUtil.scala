package com.sillycat.winnersellerserver.util

import com.sillycat.winnersellerserver.CrossDomainHeaders.`Access-Control-Allow-Origin`
import spray.http.HttpHeader
import com.typesafe.config.ConfigFactory

/**
 * Created with IntelliJ IDEA.
 * User: carl
 * Date: 6/12/13
 * Time: 3:07 PM
 * To change this template use File | Settings | File Templates.
 */
object SillycatUtil {
  def getACAOHeader(originHeader: String): HttpHeader = {

    val config = ConfigFactory.load()
    if(config.getBoolean("server.crossdomain.eanble") == true){
      val lists = config.getStringList("server.crossdomain.list")
      if(lists.contains(originHeader)){
        `Access-Control-Allow-Origin`(originHeader)
      }else{
        `Access-Control-Allow-Origin`("")
      }
    }else{
      `Access-Control-Allow-Origin`("")
    }
  }
}
