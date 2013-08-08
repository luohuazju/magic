package com.sillycat.winnersellerserver.bootstrap

import com.sillycat.winnersellerserver.dao.BaseDAO
import com.sillycat.winnersellerserver.model.NavBar
import com.sillycat.winnersellerserver.model.NavBarProtocol
import com.sillycat.winnersellerserver.dao.BaseDAO
import spray.json._
import spray.routing.authentication._
import com.sillycat.winnersellerserver.service.auth.{ DigbyDirectives, BrandUserPassAuthenticator }
import BaseDAO.threadLocalSession
import com.sillycat.winnersellerserver.util.SillycatUtil
//import com.sillycat.winnersellerserver.patch.CustomerMethodDirectives

import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Created with IntelliJ IDEA.
 * User: carl
 * Date: 6/7/13
 * Time: 5:01 PM
 * To change this template use File | Settings | File Templates.
 */

trait NavBarRouterService extends BaseRouterService with DigbyDirectives {

  implicit val navbarFormatter = NavBarProtocol.NavBarJsonFormat

  def navBarRoute = {

    host("([a-zA-Z0-9]*).api.sillycat.com".r) { brandCode =>

      pathPrefix(Version) { apiVersion =>

        optionalHeaderValueByName("Origin") { originHeader =>

          respondWithHeaders(SillycatUtil.getCrossDomainHeaders(originHeader): _*) {

            authenticate(BasicAuth(new BrandUserPassAuthenticator(dao), "Realm")) { user =>
              path("navbars") {
                get {
                  complete(
                    dao.db.withSession {
                      logger.debug("Hitting the URI navbars with apiVersion=" + apiVersion + ",brandCode=" + brandCode)
                      DefaultJsonProtocol.listFormat[NavBar].write(dao.NavBars.all).toString
                    }
                  )
                }
              } ~
                options {
                  complete {
                    "OK"
                  }
                }
            }
          }
        }
      }
    }
  }

}
