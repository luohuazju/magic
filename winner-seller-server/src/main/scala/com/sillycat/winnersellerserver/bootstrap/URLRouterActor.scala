package com.sillycat.winnersellerserver.bootstrap

import akka.actor.{ Props, Actor }
import spray.routing._
import spray.routing.directives._
import spray.util.LoggingContext
import spray.http.StatusCodes._
import spray.httpx.SprayJsonSupport._
import shapeless._
import com.sillycat.winnersellerserver.service.auth.UsersAuthenticationDirectives
import com.typesafe.scalalogging.slf4j.Logging
import akka.util.Timeout
import com.sillycat.winnersellerserver.dao.BaseDAO

class URLRouterActor extends Actor with URLRouterService {
  def actorRefFactory = context
  def receive = runRoute(route)
}

trait URLRouterService extends HttpService with UsersAuthenticationDirectives with Logging {

  implicit val timeout = Timeout(30 * 1000)
  
  implicit def myExceptionHandler(implicit log: LoggingContext) =
    ExceptionHandler.fromPF {
      case e: java.lang.IllegalArgumentException => ctx =>
        logger.error("Request {} could not be handled normally", ctx.request)
        ctx.complete(BadRequest, e.getMessage)
    }

  //implicit val dao: BaseDAO = BaseDAO.apply("app")

  def route = {
    pathPrefix(Version / BrandCode) { (apiVersion, brandCode) =>
      path("navbars") {
        authenticate(customerOnly) { user =>
          get {
            logger.debug("Hitting the URI navbars with apiVersion=" + apiVersion + ",brandCode=" + brandCode)
            complete {
              //dao.db.withSession {
                //dao.Campaigns.byId(id, brandId)
              //}
              ""
            }
          }
        }
      }
    }
  }

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
}