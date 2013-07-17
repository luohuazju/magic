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

/**
 * Created with IntelliJ IDEA.
 * User: carl
 * Date: 7/14/13
 * Time: 1:16 PM
 * To change this template use File | Settings | File Templates.
 */
trait UserRouterService extends BaseRouterService with CustomerMethodDirectives {

  def userRoute = {
    pathPrefix(Version / BrandCode) { (apiVersion, brandCode) =>

      implicit val userFormatter = new UserJsonProtocol(0).UserJsonFormat

      implicit val userLogonFormatter = UserLogonJsonProtocol.UserLogonJsonFormat

      optionalHeaderValueByName("Origin") { originHeader =>

        respondWithHeaders(SillycatUtil.getCrossDomainHeaders(originHeader): _*) {

            path("auth") {
              post {
                entity(as[UserLogon]) { item =>
                  complete {
                    dao.db.withSession {
                      val userOption = dao.Users.auth(item.email,item.password)
                      userOption
                    }
                  }
                }
              } ~
              options{
                complete{
                  "OK"
                }
              }
            }
        }
      }
    }
  }
}
