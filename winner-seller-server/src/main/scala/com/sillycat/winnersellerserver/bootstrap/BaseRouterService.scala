package com.sillycat.winnersellerserver.bootstrap

import spray.routing.{ RejectionHandler, ExceptionHandler, HttpService, PathMatcher }
import com.typesafe.scalalogging.slf4j.Logging
import shapeless.{ HNil }
import spray.util.LoggingContext
import spray.http.StatusCodes._
import scala.Some
import shapeless.::
import java.util.{ Arrays, HashMap => JMap }
import com.sillycat.winnersellerserver.dao.BaseDAO
import akka.util.Timeout
import akka.actor.ActorRefFactory
import scala.concurrent.ExecutionContext
import spray.routing.MissingCookieRejection
import spray.routing.AuthenticationFailedRejection
import scala.collection.immutable.{ :: => immutablePlus }

/**
 * Created with IntelliJ IDEA.
 * User: carl
 * Date: 6/7/13
 * Time: 5:05 PM
 * To change this template use File | Settings | File Templates.
 */
trait BaseRouterService extends HttpService with Logging {

  implicit val timeout = Timeout(60 * 1000)

  //implicit def fromActorRefFactory(factory: ActorRefFactory): ExecutionContext = factory.dispatcher

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

  val BrandCode = Segment

  val ProductType = Segment

  implicit def myExceptionHandler(implicit log: LoggingContext) =
    ExceptionHandler {
      case e: java.lang.IllegalArgumentException => ctx => {
        logger.error("Request {} could not be handled normally" + ctx.request)
        ctx.complete(BadRequest, e.getMessage)
      }

    }

  implicit val myRejectionHandler = RejectionHandler {
    case MissingCookieRejection(cookieName) immutablePlus _ => {
      complete(BadRequest, "No cookies, no service!!!")
    }
    case AuthenticationFailedRejection(realm) immutablePlus _ => {
      complete(BadRequest, "No authentication, no service!!!")
    }
  }
}
