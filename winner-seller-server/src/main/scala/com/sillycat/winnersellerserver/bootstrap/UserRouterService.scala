package com.sillycat.winnersellerserver.bootstrap

import com.sillycat.winnersellerserver.patch.CustomerMethodDirectives
import com.sillycat.winnersellerserver.model.UserJsonProtocol
import com.sillycat.winnersellerserver.util.SillycatUtil
import spray.routing.authentication.BasicAuth
import com.sillycat.winnersellerserver.service.auth.BrandUserPassAuthenticator
import com.sillycat.winnersellerserver.dao.BaseDAO
import BaseDAO.threadLocalSession
import spray.json._
import spray.httpx.SprayJsonSupport._

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

      optionalHeaderValueByName("Origin") { originHeader =>

        respondWithHeaders(SillycatUtil.getCrossDomainHeaders(originHeader): _*) {

          authenticate(BasicAuth(new BrandUserPassAuthenticator(dao), "Realm")) { user =>
            path("users") {
              get {
                parameters('email.as[String], 'password.as[String]) { (email, password)  =>
                  complete(
                    dao.db withSession {
                      dao.Users.auth(email,password) match {
                        case Some(user) => user
                        case _ => throw new IllegalArgumentException("Email and Password not match!")
                      }
                    }
                  )
                }
              }
            }
          }
        }
      }
    }
  }
}
