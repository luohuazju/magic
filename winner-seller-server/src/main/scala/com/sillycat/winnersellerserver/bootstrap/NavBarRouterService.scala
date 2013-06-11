package com.sillycat.winnersellerserver.bootstrap

import com.sillycat.winnersellerserver.dao.BaseDAO
import com.sillycat.winnersellerserver.model.NavBar
import com.sillycat.winnersellerserver.model.NavBarProtocol
import spray.json._

import spray.http._
import spray.http.MediaTypes._

import spray.routing.authentication._
import java.io.BufferedInputStream
import java.io.FileInputStream
import com.sillycat.winnersellerserver.service.auth.BrandUserPassAuthenticator
import BaseDAO.threadLocalSession

/**
 * Created with IntelliJ IDEA.
 * User: carl
 * Date: 6/7/13
 * Time: 5:01 PM
 * To change this template use File | Settings | File Templates.
 */

trait NavBarRouterService extends BaseRouterService {

    implicit val navbarFormatter = NavBarProtocol.NavBarJsonFormat

    def navBarRoute = {
      pathPrefix(Version / BrandCode) { (apiVersion, brandCode) =>
        authenticate(BasicAuth(new BrandUserPassAuthenticator(dao), "Realm")) { user =>
          path("navbars") {
            jsonpWithParameter("callback") {
              complete(HttpBody(`application/json`,
                dao.db.withSession {
                  logger.debug("Hitting the URI navbars with apiVersion=" + apiVersion + ",brandCode=" + brandCode)
                  DefaultJsonProtocol.listFormat[NavBar].write(dao.NavBars.all).toString
                }
              ))
            }
          } ~
            path("gif1x1"){
              val bis = new BufferedInputStream(new FileInputStream("/var/tmp/1x1t.gif"))
              val bArray = Stream.continually(bis.read).takeWhile(-1 !=).map(_.toByte).toArray
              bis.close();
              complete(HttpBody(`image/gif`, bArray ))
            }
        }
      }
    }


  }
