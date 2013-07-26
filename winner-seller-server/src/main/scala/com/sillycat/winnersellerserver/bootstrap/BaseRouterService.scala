package com.sillycat.winnersellerserver.bootstrap

import spray.routing.{ MethodRejection, ExceptionHandler, HttpService }
import com.typesafe.scalalogging.slf4j.Logging
import spray.routing.directives.PathMatcher
import shapeless.{ HNil, :: }
import spray.util.LoggingContext
import spray.http.StatusCodes._
import scala.Some
import shapeless.::
import com.sillycat.winnersellerserver.dao.BaseDAO
import akka.util.Timeout

/**
 * Created with IntelliJ IDEA.
 * User: carl
 * Date: 6/7/13
 * Time: 5:05 PM
 * To change this template use File | Settings | File Templates.
 */
trait BaseRouterService extends HttpService with Logging {

  implicit val timeout = Timeout(60 * 1000)

  implicit val dao: BaseDAO = BaseDAO.apply("app")

  val Version = PathMatcher("""v([0-9]+)""".r)
    .flatMap {
      case string :: HNil => {
        try Some(java.lang.Integer.parseInt(string) :: HNil)
        catch {
          case _: NumberFormatException => None
        }
      }
    }

  val BrandCode = PathElement

  val ProductType = PathElement

  implicit def myExceptionHandler(implicit log: LoggingContext) =
    ExceptionHandler.fromPF {
      case e: java.lang.IllegalArgumentException => ctx => {
        logger.error("Request {} could not be handled normally" + ctx.request)
        ctx.complete(BadRequest, e.getMessage)
      }

    }
}
