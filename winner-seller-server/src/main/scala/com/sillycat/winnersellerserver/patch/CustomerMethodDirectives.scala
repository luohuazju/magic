package com.sillycat.winnersellerserver.patch

import spray.routing.directives.MethodDirectives
import spray.http.HttpMethods._

/**
 * Created with IntelliJ IDEA.
 * User: carl
 * Date: 6/14/13
 * Time: 11:38 PM
 * To change this template use File | Settings | File Templates.
 */
trait CustomerMethodDirectives extends MethodDirectives{
  val options = method(OPTIONS)
}
