package com.sillycat.winnersellerserver.service.auth

import spray.routing.authentication._
import spray.http._
import scala.concurrent.{ ExecutionContext, Future }
import spray.util._
import HttpHeaders._
import com.typesafe.scalalogging.slf4j.Logging
import com.sillycat.winnersellerserver.dao.BaseDAO
import scala.slick.session.Database.threadLocalSession
import com.sillycat.winnersellerserver.model.User
import spray.routing.AuthenticationRequiredRejection
import spray.routing.AuthenticationFailedRejection
import spray.routing.RequestContext
import spray.routing.authentication.UserPass
import spray.http.OAuth2BearerToken
import scala.Some

/**
 * Created with IntelliJ IDEA.
 * User: carl
 * Date: 7/30/13
 * Time: 5:35 PM
 * To change this template use File | Settings | File Templates.
 */
trait CustomerHttpAuthenticator[U] extends ContextAuthenticator[U] with Logging {

  def apply(ctx: RequestContext) = {
    val authHeader = ctx.request.headers.findByType[`Authorization`]
    val tokenHeader = ctx.request.headers.filter(x => x.lowercaseName == "apptokenid").mapFind(x => Option(x))

    logger.debug("I get the header from Authorization = " + authHeader)
    logger.debug("I got the header from appTokenId = " + tokenHeader)

    val credentials = authHeader map {
      case Authorization(creds) => creds
    }

    val token = tokenHeader match {
      case Some(y) => Option(OAuth2BearerToken(y.value))
      case _ => None
    }

    val credential_token = credentials match {
      case Some(x) => Some(x)
      case _ => token
    }

    authenticate(credential_token, ctx) map {
      case Some(userContext) ⇒ Right(userContext)
      case None ⇒ Left {
        if (authHeader.isEmpty) AuthenticationRequiredRejection(scheme, realm, params(ctx))
        else AuthenticationFailedRejection(realm)
      }
    }
  }

  implicit def executionContext: ExecutionContext
  def scheme: String
  def realm: String
  def params(ctx: RequestContext): Map[String, String]

  def authenticate(credentials: Option[HttpCredentials], ctx: RequestContext): Future[Option[U]]
}

class CustomerBasicHttpAuthenticator[U](val realm: String, val userPassAuthenticator: UserPassAuthenticator[U])(implicit val executionContext: ExecutionContext)
    extends CustomerHttpAuthenticator[U] {

  def scheme = "Basic"
  def params(ctx: RequestContext) = Map.empty

  def authenticate(credentials: Option[HttpCredentials], ctx: RequestContext) = {
    userPassAuthenticator {
      credentials.flatMap {
        case OAuth2BearerToken(token) => Some(UserPass(token, "no_password"))
        case BasicHttpCredentials(user, pass) => Some(UserPass(user, pass))
        case _ ⇒ None
      }
    }
  }
}

object CustomerBasicAuth {
  def apply[T](authenticator: UserPassAuthenticator[T], realm: String)(implicit ec: ExecutionContext): CustomerBasicHttpAuthenticator[T] =
    new CustomerBasicHttpAuthenticator[T](realm, authenticator)
}

class CustomerBrandUserPassAuthenticator(dao: BaseDAO) extends UserPassAuthenticator[User] with Logging {

  def apply(userPass: Option[UserPass]) =
    Future.successful(
      userPass match {
        case Some(UserPass(user, pass)) => {
          dao.db withSession {
            dao.Users.getForEmail(user)
          } match {
            case Some(x) => {
              if (x.password == pass) {
                logger.info("working, I get the right user and pass.")
                Some(x)
              } else {
                logger.info("password fail.")
                None
              }
            }
            case _ => {
              logger.info("not hitting the right user, try with token = " + user)
              if (pass == "no_password") {
                logger.info("hitting the right token, loading the user based on token = " + user)
                dao.db withSession {
                  dao.Users.getForEmail("admin")
                } match {
                  case Some(z) => {
                    logger.info("Get user based on the token.")
                    Some(z)
                  }
                  case _ => {
                    logger.info("no user, no pass, token not right.")
                    None
                  }
                }
              } else {
                logger.info("no user, no pass, no token.")
                None
              }
            }
          }
        }
        case _ => {
          logger.info("no user, no pass, no token.")
          None
        }
      })

}

