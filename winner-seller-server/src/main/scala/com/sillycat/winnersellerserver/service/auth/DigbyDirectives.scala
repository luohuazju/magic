package com.sillycat.winnersellerserver.service.auth

import spray.routing.directives.{ PathDirectives, SecurityDirectives, HostDirectives, HeaderDirectives }
import scala.concurrent.ExecutionContext.Implicits.global
import spray.routing.PathMatcher
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
import spray.routing.AuthenticationRequiredRejection
import scala.collection.immutable.{ :: => immutablePlus }
import com.sillycat.winnersellerserver.service.auth.CustomerUsersAuthenticationDirectives

/**
 * Created by carl on 8/8/13.
 */
trait DigbyDirectives
    extends CustomerUsersAuthenticationDirectives
    with HeaderDirectives
    with HostDirectives
    with SecurityDirectives
    with PathDirectives {

  val Version = PathMatcher("""v([0-9]+)""".r)
    .flatMap {
      case string :: HNil => {
        try Some(java.lang.Integer.parseInt(string) :: HNil)
        catch {
          case _: NumberFormatException => None
        }
      }
    }

  val digbyDirective = host("([a-zA-Z0-9]*).api.sillycat.com".r) & authenticate(userPassToken) & pathPrefix(Version)

}
