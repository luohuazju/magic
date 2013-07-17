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

//      implicit val userFormatter = new UserJsonProtocol(0).UserJsonFormat

      implicit val userLogonFormatter = UserLogonJsonProtocol.UserLogonJsonFormat

      optionalHeaderValueByName("Origin") { originHeader =>

        respondWithHeaders(SillycatUtil.getCrossDomainHeaders(originHeader): _*) {
//            path("users" / PathElement ){ email =>
//              println("000000000000" + email)
//              get {
//                complete("asdfasdf")
//              }
//            } ~
            path("users") {
//              println("111111111111")
//              get {
//                parameters('email.as[String], 'password.as[String]) { (email, password)  =>
//                  println("2222222222222" + email + "22222" + password)
//                  complete(
//                    dao.db withSession {
//                      dao.Users.auth(email,password) match {
//                        case Some(user) => user
//                        case _ => throw new IllegalArgumentException("Email and Password not match!")
//                      }
//                    }
//                  )
//                }
//              } ~
//              get {
//                  println("3333333333333")
//                  complete(
//                    "asdfasdfsdf"
//                  )
//              } ~
              post {
                entity(as[UserLogon]) { item =>
                  println("44444444444444")
                  complete {
                    "asdfasdfdsaf"
                  }
                }
              } ~
//              put {
//                entity(as[User]) { item =>
//                  println("5555555555555")
//                  complete {
//                    "asdfasdfdsaf"
//                  }
//                }
//              }
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
