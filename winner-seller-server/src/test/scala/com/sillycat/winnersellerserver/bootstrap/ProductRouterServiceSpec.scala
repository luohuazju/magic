package com.sillycat.winnersellerserver.bootstrap

import org.scalatest.FunSuite
import spray.routing.{ AuthenticationFailedRejection, Directives }
import spray.testkit.ScalatestRouteTest
import spray.http.HttpHeaders.{ Host, Authorization }
import org.scalatest._
import spray.http._
import spray.routing._
import spray.http.HttpHeaders.{ Host, Authorization }

import com.sillycat.winnersellerserver.model.ProductJsonProtocol
import com.sillycat.winnersellerserver.model.Product
import com.sillycat.winnersellerserver.dao.BaseDAO
import spray.json._
import spray.httpx.SprayJsonSupport._

/**
 * Created with IntelliJ IDEA.
 * User: carl
 * Date: 7/30/13
 * Time: 11:38 AM
 * To change this template use File | Settings | File Templates.
 */
class ProductRouterServiceSpec extends FunSuite with Directives
    with ScalatestRouteTest
    with ProductRouterService {

  def actorRefFactory = system

  val kikoBrand = Host("kiko.api.sillycat.com")

  val adminAuth = BasicHttpCredentials("admin@gmail.com", "admin")

  val managerAuth = BasicHttpCredentials("manager@gmail.com", "manager")

  implicit val productFormatter = ProductJsonProtocol.ProductJsonFormat

  test("ProductRouterService /products GET success") {
    Get("/v1/products?productType=PLAN") ~> addHeader(kikoBrand) ~> addHeader(Authorization(adminAuth)) ~>
      productRoute ~> check {
        Thread.sleep(2000)
        val entity = entityAs[String]
        info("entity=" + entity)
        val products = DefaultJsonProtocol.listFormat[Product].read(entity.asJson)
        info("products=" + products)
        assert(products.size > 0)
      }
  }

  test("ProductRouterService /products GET fail with authentication") {
    Get("/v1/products?productType=PLAN") ~> addHeader(kikoBrand) ~> addHeader(Authorization(BasicHttpCredentials("admi11n@gmail.com", "adm11in"))) ~>
      productRoute ~> check {
        info("rejection=" + rejection)
        assert(rejection === AuthenticationFailedRejection("Realm"))
      }
  }

  test("ProductRouterService /products GET fail with authorization") {
    Get("/v1/products?productType=PLAN") ~> addHeader(kikoBrand) ~> addHeader(Authorization(managerAuth)) ~>
      productRoute ~> check {
        info("rejection=" + rejection)
        assert(rejection === AuthorizationFailedRejection)
      }
  }

}
