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

/**
 * Created with IntelliJ IDEA.
 * User: carl
 * Date: 6/7/13
 * Time: 5:11 PM
 * To change this template use File | Settings | File Templates.
 */
trait ProductRouterService extends BaseRouterService {
  def productRoute = {
    pathPrefix(Version / BrandCode) { (apiVersion, brandCode) =>
      authenticate(BasicAuth(new BrandUserPassAuthenticator(dao), "Realm")) { user =>

        implicit val productFormatter = ProductJsonProtocol.ProductJsonFormat

        path("products") {
          get {
            //fetch list
            complete(HttpBody(`application/json`,
              dao.db.withSession {
                logger.debug("Hitting to fetch products with apiVersion=" + apiVersion + ",brandCode=" + brandCode)
                DefaultJsonProtocol.listFormat[Product].write(dao.Products.all).toString
              }
            ))
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
                    ???
                  }
                }
              }
          }
        }~
        path("products" / IntNumber) { id =>
          get {
            complete {
              dao.db withSession {
                dao.Products.byId(id)
              }
            }
          } ~
          delete {
            complete {
              dao.db withSession {
                //TODO call delete api here
                ???
              }
            }
          }
        }
      }
    }
  }
}
