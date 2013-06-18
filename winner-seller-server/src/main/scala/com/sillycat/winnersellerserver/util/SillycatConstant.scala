package com.sillycat.winnersellerserver.util

import org.joda.time.format.DateTimeFormat
import com.sillycat.winnersellerserver.CrossDomainHeaders.{`Access-Control-Allow-Headers`, `Access-Control-Allow-Origin`}
import spray.json.JsValue

/**
 * Created with IntelliJ IDEA.
 * User: carl
 * Date: 6/11/13
 * Time: 11:13 PM
 * To change this template use File | Settings | File Templates.
 */
object SillycatConstant {
  val DATE_TIME_FORMAT_1 = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")

  val DATE_TIME_FORMAT_2 = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm")

  val EMPTY_JS_VALUE = Map[String, JsValue]()

}
