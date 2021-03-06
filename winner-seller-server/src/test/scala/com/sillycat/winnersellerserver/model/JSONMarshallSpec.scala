package com.sillycat.winnersellerserver.model

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import spray.json.DefaultJsonProtocol
import spray.json.pimpAny
import spray.json.pimpString

class JSONMarshallSpec extends FlatSpec with ShouldMatchers with DefaultJsonProtocol {

  import com.sillycat.winnersellerserver.model.NavBarProtocol._
  import com.sillycat.winnersellerserver.model.ProductJsonProtocol._
  import com.sillycat.winnersellerserver.model.CartJsonProtocol._

  "Marshalling User without ID JSON" should "result in an User Object without id" in {
    val jsonUser = """{
    		"userName":"Carl",
    		"age": 31,
    		"userType": "ADMIN", 
    		"createDate": "2012-05-21 12:34", 
    		"expirationDate": "2013-05-12 12:34",
    		"password": "111111",
        "email": "admin@gmail.com"
      }"""
    implicit val formatter = (new UserJsonProtocol(1)).UserJsonFormat
    val userAST = jsonUser.asJson

    info("UserAST: " + userAST.prettyPrint)
    val user: User = userAST.convertTo[User]
    info("User Object: " + user.toJson)
    assert(user.age === 31)
    assert(user.id === None)
  }

  "Marshalling Simple NavBar JSON" should "result in a Simple NavBar Object" in {
    val json =
      """{
        "title":   "首页",
        "link":    "#",
        "alter":   "test"
        }"""
    val objAST = json.asJson

    info("NavBarAST: " + objAST.asJsObject)
    val obj: NavBar = objAST.convertTo[NavBar]
    info("NavBar Object: " + obj.toJson)
    assert(obj.toJson === objAST)
  }

  "Marshalling Complex NavBar JSON" should "result in a Complex NavBar Object" in {
    val json =
      """{
        "id" :  1,
        "title":   "title1",
        "link":    "#",
        "alter":   "",
        "parentId" : 12,
        "subs" : [
                  {
                    "title":  "title2",
                    "link":   "#productsPlan",
                    "alter":  ""
                  }
               ],
         "parent" :
               {
                    "id" : 9,
                    "title": "test1",
                    "link" : "#",
                    "alter" : "alter"
               }
      }"""
    val objAST = json.asJson

    info("NavBarAST: " + objAST.asJsObject)
    val obj: NavBar = objAST.convertTo[NavBar]
    info("NavBar Object: " + obj.toJson)
    assert(obj.toJson === objAST)
  }

  "Marshalling Cart JSON" should "result in an Cart Object" in {
    val jsonCart = """{
    		"id" : 2,
    		"cartName" : "HomeCart",
    		"cartType" : "DIRECT",
    		"user" : {
    			"id" : 3,
    			"userName" : "Carl",
    			"age": 31,
    			"userType": "ADMIN",
    			"createDate": "2012-05-21 12:34",
    			"expirationDate": "2013-05-12 12:34",
    			"password": "111111",
          "email": "admin@gmail.com"
    		},
    		"products" : [
    			{
    				"id" : 1,
    				"productName" : "iphone 5",
    				"productDesn" : "A good mobile device.",
    				"createDate" : "2012-05-22 13:33",
    				"expirationDate" : "2012-05-22 14:33",
    				"productCode" : "IPHONE5",
            "productPriceUS" : 30.32,
            "productPriceCN" : 400,
            "productSellingPriceCN" : 200,
            "productWeight" : 1.0,
            "productWin" : 100,
            "productLink" : "link",
            "productType" : "PLAN",
            "productStatus" : "ACTIVE"
    			},
    			{
    				"id" : 2,
    				"productName" : "iphone 4s",
    				"productDesn" : "A good mobile device.",
    				"createDate" : "2012-05-22 13:33",
    				"expirationDate" : "2012-05-22 14:33",
    				"productCode" : "IPHONE4S",
            "productPriceUS" : 30.32,
            "productPriceCN" : 400,
            "productSellingPriceCN" : 200,
            "productWeight" : 1.0,
            "productWin" : 100,
            "productLink" : "link",
            "productType" : "PLAN",
            "productStatus" : "ACTIVE"
    			}
            ]
      }"""
    val cartAST = jsonCart.asJson

    info("CartAST: " + cartAST.asJsObject)
    val cart: Cart = cartAST.convertTo[Cart]
    info("Cart Object: " + cart.toJson)
    assert(cart.toJson === cartAST)
  }

  "Marshalling Product JSON" should "result in an Product Object" in {
    val jsonProduct = """{
    			"id" : 1,
    			"productName" : "iphone 5",
    			"productDesn" : "A good mobile device.",
    			"createDate" : "2012-05-22 13:33",
    			"expirationDate" : "2012-05-22 14:33",
    			"productCode" : "IPHONE5",
          "productPriceUS" : 30.32,
          "productPriceCN" : 400,
          "productSellingPriceCN" : 200,
          "productWeight" : 1.0,
          "productWin" : 100,
          "productLink" : "link",
          "productType" : "PLAN",
          "productStatus" : "ACTIVE"
    		}"""
    val productAST = jsonProduct.asJson

    info("ProductAST: " + productAST.asJsObject)
    val product: Product = productAST.convertTo[Product]
    info("Product Object: " + product.toJson)
    assert(product.toJson === productAST)
  }

  "Marshalling User JSON" should "result in an User Object" in {
    val jsonUser = """{
    		"id": 1 ,
    		"userName":"Carl",
    		"age": 31, 
    		"userType": "ADMIN", 
    		"createDate": "2012-05-21 12:34", 
    		"expirationDate": "2013-05-12 12:34",
    		"password": "111111",
        "email": "admin@gmail.com"
      }"""
    implicit val formatter = (new UserJsonProtocol(1)).UserJsonFormat
    val userAST = jsonUser.asJson

    info("UserAST: " + userAST.prettyPrint)
    val user: User = userAST.convertTo[User]
    info("User Object: " + user.toJson)
    assert(user.toJson === userAST)
  }

}