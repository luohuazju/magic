package com.sillycat.winnersellerserver.bootstrap

import akka.actor.Actor
import spray.routing._
import spray.routing.directives._
import spray.util.LoggingContext
import spray.http.StatusCodes._
import shapeless._
import com.sillycat.winnersellerserver.service.auth.UsersAuthenticationDirectives
import akka.util.Timeout
import com.sillycat.winnersellerserver.dao.BaseDAO
import com.sillycat.winnersellerserver.model.NavBar
import com.sillycat.winnersellerserver.model.NavBarProtocol
import spray.json._
import org.joda.time.format.DateTimeFormat
import org.joda.time.DateTime
import scala.Some
import spray.http._
import spray.http.MediaTypes._
import scala.Some
import shapeless.::
import akka.actor.{ Props, Actor }
import spray.routing._
import spray.routing.directives._
import spray.util.LoggingContext
import spray.http.StatusCodes._
import spray.httpx.SprayJsonSupport._
import shapeless._

import spray.routing.authentication._

import java.io.File
import org.parboiled.common.FileUtils
import java.io.BufferedInputStream
import java.io.FileInputStream

class URLRouterActor extends Actor with URLRouterService {
  def actorRefFactory = context
  def receive = runRoute(route)
}

trait URLRouterService extends HttpService with UsersAuthenticationDirectives {
  import BaseDAO.threadLocalSession

  implicit val timeout = Timeout(30 * 1000)
  
  implicit def myExceptionHandler(implicit log: LoggingContext) =
    ExceptionHandler.fromPF {
      case e: java.lang.IllegalArgumentException => ctx =>
        logger.error("Request {} could not be handled normally" + ctx.request)
        ctx.complete(BadRequest, e.getMessage)
    }

  implicit val navbarFormatter = NavBarProtocol.NavBarJsonFormat

  def route = {
    pathPrefix(Version / BrandCode) { (apiVersion, brandCode) =>
      //authenticate(customerOnly) { user =>
        path("navbars") {
          //respondWithMediaType(`application/json`) {
          //  get {
                jsonpWithParameter("callback") {
                  //complete(HttpBody(`application/json`,"""{ "key" : "value" }"""))
                  complete(HttpBody(`application/json`,
                    dao.db.withSession {
                      logger.debug("Hitting the URI navbars with apiVersion=" + apiVersion + ",brandCode=" + brandCode)
                      DefaultJsonProtocol.listFormat[NavBar].write(dao.NavBars.all).toString
                    }
                  ))
          //    }
          //  }
          }
        } ~
        path("gif1x1"){
          val bis = new BufferedInputStream(new FileInputStream("/var/tmp/1x1t.gif"))
          val bArray = Stream.continually(bis.read).takeWhile(-1 !=).map(_.toByte).toArray
          bis.close();
          complete(HttpBody(`image/gif`, bArray ))
        }
      //}
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