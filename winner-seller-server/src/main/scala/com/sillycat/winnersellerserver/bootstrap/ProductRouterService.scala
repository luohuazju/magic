package com.sillycat.winnersellerserver.bootstrap


import com.sillycat.winnersellerserver.model.ProductJsonProtocol
import com.sillycat.winnersellerserver.model.Product
import com.sillycat.winnersellerserver.dao.BaseDAO
import spray.json._
import spray.http._
import spray.http.MediaTypes._
import spray.httpx.SprayJsonSupport._
import spray.routing.authentication._
import com.sillycat.winnersellerserver.service.auth.BrandUserPassAuthenticator
import BaseDAO.threadLocalSession
import spray.http.HttpHeaders._
import com.sillycat.winnersellerserver.CrossDomainHeaders._
import com.sillycat.winnersellerserver.util.SillycatConstant
import com.sillycat.winnersellerserver.util.SillycatUtil
import com.sillycat.winnersellerserver.patch.CustomerMethodDirectives

/**
 * Created with IntelliJ IDEA.
 * User: carl
 * Date: 6/7/13
 * Time: 5:11 PM
 * To change this template use File | Settings | File Templates.
 */
trait ProductRouterService extends BaseRouterService with CustomerMethodDirectives {


  def productRoute = {
    pathPrefix(Version / BrandCode) { (apiVersion, brandCode) =>
      implicit val productFormatter = ProductJsonProtocol.ProductJsonFormat

      //optionalHeaderValueByName("Origin") { originHeader =>
      headerValueByName("Origin") { originHeader =>

       respondWithHeaders(SillycatUtil.getCrossDomainHeaders(originHeader): _*) {

        authenticate(BasicAuth(new BrandUserPassAuthenticator(dao), "Realm")) { user =>
            path("products") {
              get {
                complete(
                  dao.db.withSession {
                    logger.debug("Hitting to fetch products with apiVersion=" + apiVersion + ",brandCode=" + brandCode)
                    DefaultJsonProtocol.listFormat[Product].write(dao.Products.all).toString
                  }
                )
              } ~
              post {
                entity(as[Product]) { item =>
                  complete {
                    dao.db.withSession {
                      logger.debug("Hitting to create product with apiVersion=" + apiVersion + ",brandCode=" + brandCode)
                      dao.Products.insert(item)
                    }
                  }
                }
              } ~
              put {
                 entity(as[Product]){ item =>
                    complete {
                      dao.db withSession {
                        logger.debug("Hitting to update product with apiVersion=" + apiVersion + ",brandCode=" + brandCode)
                        dao.Products.update(item)
                      }
                    }
                 }
              } ~
              options{
                complete{
                  "OK"
                }
              }
            }~
            path("products" / IntNumber) { id =>
              get {
                complete {
                  dao.db withSession {
                    logger.debug("Hitting to getById product with apiVersion=" + apiVersion + ",brandCode=" + brandCode)
                    dao.Products.byId(id)
                  }
                }
              } ~
              delete {
                complete {
                  dao.db withSession {
                    logger.debug("Hitting to deleteById product with apiVersion=" + apiVersion + ",brandCode=" + brandCode)
                    dao.Products.deleteById(id) + ""
                  }
                }
              }~
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
}
