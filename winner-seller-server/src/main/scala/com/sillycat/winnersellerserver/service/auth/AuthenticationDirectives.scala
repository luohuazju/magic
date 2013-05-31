package com.sillycat.winnersellerserver.service.auth

import scala.concurrent.Future
import com.sillycat.winnersellerserver.dao.BaseDAO
import com.sillycat.winnersellerserver.model.User
import spray.http.BasicHttpCredentials
import spray.http.HttpHeaders.Authorization
import spray.routing.AuthenticationFailedRejection
import spray.routing.AuthenticationRequiredRejection
import spray.routing.HttpService
import spray.routing.RequestContext
import spray.routing.authentication.Authentication
import spray.routing.authentication.UserPass
import spray.util.pimpSeq
import com.typesafe.scalalogging.slf4j.Logging

trait AuthenticationDirectives extends Logging {
  this: HttpService =>

  //val logger = LoggerFactory.getLogger(this.getClass().getName())
    
  def doAuthenticate(userName: String, password: String): Future[Option[User]]

  def adminOnly: RequestContext => Future[Authentication[User]] = {
    ctx: RequestContext =>
      logger.debug("Auth the adminOnly function.")
      val userPass = getToken(ctx)
      if (userPass.isEmpty)
        Future(Left(AuthenticationRequiredRejection("https", "sillycat")))
      else doAuthenticate(userPass.get.user, userPass.get.pass).map {
        user =>
          if (user.isDefined)
            Right(user.get)
          else
            Left(AuthenticationFailedRejection("sillycat"))
      }
  }

  def customerOnly: RequestContext => Future[Authentication[User]] = {
    ctx: RequestContext =>
      logger.debug("Auth the customerOnly function.")
      val userPass = getToken(ctx)
      if (userPass.isEmpty)
        Future(Left(AuthenticationRequiredRejection("https", "sillycat")))
      else doAuthenticate(userPass.get.user, userPass.get.pass).map {
        user =>
          if (user.isDefined)
            Right(user.get)
          else
            Left(AuthenticationFailedRejection("sillycat"))
      }
  }

  def withRole(roleCode: String): RequestContext => Future[Authentication[User]] = {
    ctx: RequestContext =>
      logger.debug("Auth the withRole function.")
      val userPass = getToken(ctx)
      if (userPass.isEmpty)
        Future(Left(AuthenticationRequiredRejection("https", "sillycat")))
      else doAuthenticate(userPass.get.user, userPass.get.pass).map {
        user =>
          if (user.isDefined)
            Right(user.get)
          else
            Left(AuthenticationFailedRejection("sillycat"))
      }
  }

  def getToken(ctx: RequestContext): Option[UserPass] = {
    val authHeader = ctx.request.headers.findByType[Authorization]
    val credentials = authHeader.map { case Authorization(creds) => creds }
    logger.debug("Credentials from the header = " + credentials)
    credentials.flatMap {
      case BasicHttpCredentials(user, pass) =>
        logger.debug("Digest from the header, user = " + user + " pass = " + pass)
        Some(UserPass(user, pass))
      case _ => None
    }
  }
}

trait UsersAuthenticationDirectives
  extends AuthenticationDirectives with Logging{
  this: HttpService =>

  import BaseDAO.threadLocalSession

  implicit val dao: BaseDAO = BaseDAO.apply("app")

  override def doAuthenticate(userName: String, password: String) = {
    Future {
      dao.db.withSession{
    	  dao.Users.auth(userName, password)
      }
    }
  }
}