package com.sillycat.winnersellerserver.bootstrap

import com.sillycat.winnersellerserver.patch.CustomerMethodDirectives
import com.sillycat.winnersellerserver.model._
import com.sillycat.winnersellerserver.util.SillycatUtil
import spray.routing.authentication.BasicAuth
import com.sillycat.winnersellerserver.service.auth.BrandUserPassAuthenticator
import com.sillycat.winnersellerserver.dao.BaseDAO
import BaseDAO.threadLocalSession
import spray.json._
import spray.httpx.SprayJsonSupport._
import com.sillycat.winnersellerserver.model.UserLogon
import spray.http.StatusCodes._
import com.sillycat.winnersellerserver.model.UserLogon
import scala.Some

/**
 * Created with IntelliJ IDEA.
 * User: carl
 * Date: 7/14/13
 * Time: 1:16 PM
 * To change this template use File | Settings | File Templates.
 */
trait UserRouterService extends BaseRouterService with CustomerMethodDirectives {

  def userRoute = {
    host("([a-zA-Z0-9]*).api.sillycat.com".r) { brandCode =>

      pathPrefix(Version) { apiVersion =>

        implicit val userFormatter = new UserJsonProtocol(0).UserJsonFormat

        implicit val userLogonFormatter = UserLogonJsonProtocol.UserLogonJsonFormat

        optionalHeaderValueByName("Origin") { originHeader =>

          respondWithHeaders(SillycatUtil.getCrossDomainHeaders(originHeader): _*) {

            options {
              complete {
                "OK"
              }
            } ~
              path("auth") {
                post {
                  entity(as[UserLogon]) { item =>
                    dao.db.withSession {
                      dao.Users.auth(item.email, item.password) match {
                        case Some(user) => complete { user }
                        case _ => complete("{ status: error, message: error message }")
                      }
                    }
                  }
                }
              }
          }
        }
      }
    }
  }
}
