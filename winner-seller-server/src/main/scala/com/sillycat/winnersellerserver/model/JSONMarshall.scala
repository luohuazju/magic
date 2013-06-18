package com.sillycat.winnersellerserver.model

import org.joda.time.format.DateTimeFormat
import org.joda.time.DateTime
import scala.Some
import com.typesafe.scalalogging.slf4j.Logging
import spray.httpx.SprayJsonSupport
import spray.json._
import com.sillycat.winnersellerserver.util.SillycatConstant


class UserJsonProtocol(currentId: Long) extends DefaultJsonProtocol {

  private val dateTimeFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm")

  private val emptyJSMap = Map[String, JsValue]()

  implicit object UserJsonFormat extends RootJsonFormat[User] {
    def write(user: User) = JsObject(
      Map(  
      "userName" -> JsString(user.userName),
      "password" -> JsString(user.password),
      "age" -> JsNumber(user.age),
      "userType" -> JsString(user.userType.toString()),
      "createDate" -> JsString(dateTimeFormat.print(new DateTime(user.createDate))),
      "expirationDate" -> JsString(dateTimeFormat.print(new DateTime(user.expirationDate)))
      ) ++ 
      user.id.map( i => Map("id" -> JsNumber(i))).getOrElse(emptyJSMap)
    )
    def read(jsUser: JsValue) = {
      jsUser.asJsObject.getFields("id", "userName", "age", "userType", "createDate", "expirationDate", "password") match {
        case Seq(JsNumber(id), JsString(userName), JsNumber(age), JsString(userType), JsString(createDate), JsString(expirationDate), JsString(password)) =>
          val createDateObject = dateTimeFormat.parseDateTime(createDate)
          val expirationDateObject = dateTimeFormat.parseDateTime(expirationDate)
          new User(Some(id.longValue), userName, age.toInt, UserType.withName(userType), createDateObject, expirationDateObject,password)
        case Seq(JsString(userName), JsNumber(age), JsString(userType), JsString(createDate), JsString(expirationDate), JsString(password)) =>
          val createDateObject = dateTimeFormat.parseDateTime(createDate)
          val expirationDateObject = dateTimeFormat.parseDateTime(expirationDate)
          new User( None, userName, age.toInt, UserType.withName(userType), createDateObject, expirationDateObject,password)
        case _ => throw new DeserializationException("User expected")
      }
    }
  }
}

object ProductJsonProtocol extends DefaultJsonProtocol with SprayJsonSupport {

  implicit object ProductJsonFormat extends RootJsonFormat[Product] {
    def write(product: Product) = JsObject(
      Map(
      "productName" -> JsString(product.productName),
      "createDate"	-> JsString(SillycatConstant.DATE_TIME_FORMAT_2.print(new DateTime(product.createDate))),
      "expirationDate" -> JsString(SillycatConstant.DATE_TIME_FORMAT_2.print(new DateTime(product.expirationDate))),
      "productPriceUS" -> JsNumber(product.productPriceUS),
      "productPriceCN" -> JsNumber(product.productPriceCN),
      "productSellingPriceCN" -> JsNumber(product.productSellingPriceCN),
      "productWin" -> JsNumber(product.productWin),
      "productType" -> JsString(product.productType.toString),
      "productStatus" -> JsString(product.productStatus.toString)
      ) ++
      product.id.map( i => Map("id" -> JsNumber(i))).getOrElse(SillycatConstant.EMPTY_JS_VALUE)
        ++
      product.productDesn.map( i=> Map("productDesn" -> JsString(i))).getOrElse(SillycatConstant.EMPTY_JS_VALUE)
        ++
      product.productCode.map( i=> Map("productCode" -> JsString(i))).getOrElse(SillycatConstant.EMPTY_JS_VALUE)
        ++
      product.productWeight.map( i=> Map("productWeight" -> JsNumber(i))).getOrElse(SillycatConstant.EMPTY_JS_VALUE)
        ++
      product.productLink.map( i=> Map("productLink" -> JsString(i))).getOrElse(SillycatConstant.EMPTY_JS_VALUE)
      )
    def read(jsProduct: JsValue) = {
      val params: Map[String, JsValue] = jsProduct.asJsObject.fields
      val createDate =  params("createDate").convertTo[String]
      val expirationDate = params("expirationDate").convertTo[String]
      val createDateObject = SillycatConstant.DATE_TIME_FORMAT_2.parseDateTime(createDate)
      val expirationDateObject = SillycatConstant.DATE_TIME_FORMAT_2.parseDateTime(expirationDate)

      Product(
        params.get("id").map(_.convertTo[Long]),
        params("productName").convertTo[String],
        params.get("productDesn").map(_.convertTo[String]),
        createDateObject,
        expirationDateObject,
        params.get("productCode").map(_.convertTo[String]),
        params("productPriceUS").convertTo[BigDecimal],
        params("productPriceCN").convertTo[BigDecimal],
        params("productSellingPriceCN").convertTo[BigDecimal],
        params.get("productWeight").map(_.convertTo[Double]),
        params("productWin").convertTo[BigDecimal],
        params.get("productLink").map(_.convertTo[String]),
        ProductType.withName(params("productType").convertTo[String]),
        ProductStatus.withName(params("productStatus").convertTo[String])
      )
    }
  }
}

object CartJsonProtocol extends DefaultJsonProtocol {

  private val emptyJSMap = Map[String, JsValue]()
  
  implicit object CartJsonFormat extends RootJsonFormat[Cart] {
    implicit val userFormatter = (new UserJsonProtocol(1)).UserJsonFormat
    implicit val productFormatter = ProductJsonProtocol.ProductJsonFormat
    def write(cart: Cart) = JsObject(
      Map(
      "cartName" -> JsString(cart.cartName),
      "cartType" -> JsString(cart.cartType.toString()),
      "user"     -> cart.user.toJson,
      "products" -> cart.products.toJson
      ) ++
      cart.id.map( i => Map("id" -> JsNumber(i))).getOrElse(emptyJSMap)
    )
    def read(jsCart: JsValue) = {
    	val params: Map[String, JsValue] = jsCart.asJsObject.fields
    	Cart(
    	    params.get("id").map(_.convertTo[Int]), 
    	    params("cartName").convertTo[String],
    	    CartType.withName(params("cartType").convertTo[String]),
    	    params("user").convertTo[User],
    	    params("products").convertTo[Seq[Product]]
    	)
    }
  }
}

object NavBarProtocol extends DefaultJsonProtocol with Logging {

  private val emptyJSMap = Map[String, JsValue]()

  implicit object NavBarJsonFormat extends RootJsonFormat[NavBar]{
     def write(nav : NavBar) = JsObject(
      Map(
        "title" -> JsString(nav.title),
        "link"  -> JsString(nav.link),
        "alter" -> JsString(nav.alter)
      ) ++
      nav.id.map( i => Map("id" -> JsNumber(i))).getOrElse(emptyJSMap)
      ++
      nav.parentId.map( i => Map("parentId" -> JsNumber(i))).getOrElse(emptyJSMap)
      ++
      nav.parent.map( i => Map("parent" -> i.toJson)).getOrElse(emptyJSMap)
      ++
      nav.subs.map( i => Map("subs" -> i.toJson)).getOrElse(emptyJSMap)
     )
     def read(jsNav: JsValue) = {
       val params: Map[String, JsValue] = jsNav.asJsObject.fields

       NavBar(
          params.get("id").map(_.convertTo[Long]),
          params("title").convertTo[String],
          params("link").convertTo[String],
          params("alter").convertTo[String],
          params.get("parentId").map(_.convertTo[Long]),
          params.get("subs").map(_.convertTo[List[NavBar]]),
          params.get("parent").map(_.convertTo[NavBar])
       )
     }
  }
}