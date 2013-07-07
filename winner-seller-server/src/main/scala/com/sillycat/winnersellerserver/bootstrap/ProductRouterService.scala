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
import com.sillycat.winnersellerserver.model.ProductStatus

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

      optionalHeaderValueByName("Origin") { originHeader =>

       respondWithHeaders(SillycatUtil.getCrossDomainHeaders(originHeader): _*) {

        authenticate(BasicAuth(new BrandUserPassAuthenticator(dao), "Realm")) { user =>
            path("products") {
              get {
                parameters('productType.as[String]) { productType =>
                  complete(
                    dao.db.withSession {
                      //DefaultJsonProtocol.listFormat[Product].write(dao.Products.all).toString
                      DefaultJsonProtocol.listFormat[Product].write(dao.Products.forProductTypeAndStatus(productType,ProductStatus.ACTIVE.toString)).toString
                    }
                  )
                }
              } ~
              post {
                entity(as[Product]) { item =>
                  complete {
                    dao.db.withSession {
                      dao.Products.insert(item)
                    }
                  }
                }
              } ~
              put {
                 entity(as[Product]){ item =>
                    complete {
                      dao.db withSession {
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
                    dao.Products.byId(id)
                  }
                }
              } ~
              delete {
                complete {
                  dao.db withSession {
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
