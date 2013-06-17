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

  val preflightHeaders = List(
    RawHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS"),
    RawHeader("Access-Control-Allow-Headers", "accept, origin, authorization, content-type, X-Requested-With, X-HTTP-Method-Override"),
    RawHeader("Access-Control-Max-Age", "86400"),
    RawHeader("Access-Control-Allow-Origin","http://carl.digby.com:9000"),
    RawHeader("Access-Control-Allow-Credentials","true")
  )

  def productRoute = {
    pathPrefix(Version / BrandCode) { (apiVersion, brandCode) =>
      implicit val productFormatter = ProductJsonProtocol.ProductJsonFormat
      //headerValueByName("Origin") { originHeader =>
        //println("getting the header = " + originHeader)
        //respondWithHeaders(`Access-Control-Request-Method`("POST"),`Access-Control-Request-Headers`("accept, origin, content-type"),`Access-Control-Expose-Headers`("X-Api-Version, X-Request-Id, X-Response-Time"), `Access-Control-Allow-Origin`("http://localhost:9000"),`Access-Control-Allow-Credentials`(true),`Access-Control-Allow-Methods`("*"),`Access-Control-Allow-Headers`("Origin, Accept, Accept-Version, Content-Length, Content-MD5, Content-Type, Date, X-Api-Version, X-Response-Time, X-PINGOTHER, X-CSRF-Token"), `Access-Control-Max-Age`("10000")) {
       //respondWithHeaders(`Access-Control-Allow-Origin`("*"),`Access-Control-Allow-Methods`("POST, GET, PUT, DELETE, OPTIONS"),`Access-Control-Allow-Headers`("X-Requested-With, X-HTTP-Method-Override, Content-Type, Accept")) {

      respondWithHeaders(preflightHeaders: _*) {

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
            //}
          }
        }
      }
    }
  }
}
