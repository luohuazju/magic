package com.sillycat.winnersellerserver.service.auth

import scala.concurrent.Future
import com.sillycat.winnersellerserver.dao.BaseDAO
import com.sillycat.winnersellerserver.model.User
import spray.http.{ OAuth2BearerToken, BasicHttpCredentials }
import spray.http.HttpHeaders.Authorization
import spray.routing.AuthenticationFailedRejection
import spray.routing.HttpService
import spray.routing.RequestContext
import spray.routing.authentication.Authentication
import spray.routing.authentication.UserPass
import spray.util.pimpSeq
import com.typesafe.scalalogging.slf4j.Logging
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Created with IntelliJ IDEA.
 * User: carl
 * Date: 7/31/13
 * Time: 5:38 PM
 * To change this template use File | Settings | File Templates.
 */
trait CustomerAuthenticationDirectives extends Logging {
  //this: HttpService =>

  def authPass(userName: String, password: String): Future[Option[User]]

  def authToken(token: String): Future[Option[User]]

  def userPassToken: RequestContext => Future[Authentication[User]] = {
    ctx: RequestContext =>
      val userPassToken = getToken(ctx)
      userPassToken match {
        case Some((user, pass, null)) => {
          authPass(user, pass).map {
            user =>
              if (user.isDefined)
                Right(user.get)
              else
                Left(AuthenticationFailedRejection("Realm"))
          }
        }
        case Some((null, null, token)) => {
          authToken(token).map {
            user =>
              if (user.isDefined)
                Right(user.get)
              else
                Left(AuthenticationFailedRejection("Realm"))
          }
        }
        case _ => Future(Left(AuthenticationFailedRejection("Realm")))
      }
  }

  def getToken(ctx: RequestContext): Option[(String, String, String)] = {
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

    credential_token.flatMap {
      case BasicHttpCredentials(user, pass) =>
        logger.debug("Digest from the header, user = " + user + " pass = " + pass)
        Some((user, pass, null))
      case OAuth2BearerToken(token) =>
        logger.debug("Digest from the header, token = " + token)
        Some((null, null, token))
      case _ => None
    }
  }
}

trait CustomerUsersAuthenticationDirectives
    extends CustomerAuthenticationDirectives with Logging {
  //this: HttpService =>

  import BaseDAO.threadLocalSession

  implicit val dao: BaseDAO = BaseDAO.apply("app")

  override def authPass(userName: String, password: String) = {
    Future {
      dao.db withSession {
        dao.Users.getForEmail(userName)
      } match {
        case Some(x) => {
          logger.info("x.password = " + x.password + " pass = " + password)
          if (x.password == password) {
            logger.info("working, I get the right user and pass.")
            Some(x)
          } else {
            logger.info("password fail.")
            None
          }
        }
        case _ => {
          logger.info("no user matching.")
          None
        }
      }
    }
  }

  override def authToken(token: String) = {
    Future {
      dao.db withSession {
        dao.Users.getForEmail(token)
      } match {
        case Some(x) => {
          logger.info("working, I get the right token.")
          Some(x)
        }
        case _ => {
          logger.info("no user matching the token.")
          None
        }
      }
    }
  }
}
