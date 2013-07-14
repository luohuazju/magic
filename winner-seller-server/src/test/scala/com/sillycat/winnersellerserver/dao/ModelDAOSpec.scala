package com.sillycat.winnersellerserver.dao

import org.scalatest.BeforeAndAfterAll
import org.scalatest.FunSuite
import org.specs2.matcher.ShouldMatchers
import com.sillycat.winnersellerserver.model._
import org.joda.time.DateTime
import scala.slick.session.Database.threadLocalSession

class ModelDAOSpec extends FunSuite with ShouldMatchers with BeforeAndAfterAll {

  implicit val dao: BaseDAO = BaseDAO.apply("test")

  override def beforeAll() {
    dao.create
  }

  override def afterAll() {
    dao.drop
  }

  val empty: List[Nothing] = List()

  test("Database tables are created and dropped") {
    assert("x" === "x")
  }

  test("Persist NavBar"){
    dao.db withSession {
      val item1 = NavBar(None,"title1", "link1", "", Some(0), None, None)
      val item2 = NavBar(None,"title2", "link2", "", Some(0), None, None)
      val sub1_1 = NavBar(None, "sub1_1", "link1_1", "", Some(1), None, None)
      val sub1_2 = NavBar(None, "sub1_2", "link1_2", "", Some(1), None, None)

      val id1 = dao.NavBars.insert(item1)
      assert(id1 === 1)

      dao.NavBars.insert(item2)
      dao.NavBars.insert(sub1_1)
      dao.NavBars.insert(sub1_2)
    }
  }

  test("Load NavBar All"){
    dao.db withSession {
      val all = dao.NavBars.all()
      assert(all.length === 2)

      assert(all.head.title === "title1")
      assert(all.head.subs.getOrElse(empty).length === 2)
      info(all.toString())
    }
  }
  
  test("Persist Product") {
    dao.db withSession {
      val item = Product(
          None,
          "Iphone5",
          Some("Nice device"),
          DateTime.now,
          Some(DateTime.now),
          Some("IPHONE5"),
          BigDecimal(395),
          BigDecimal(3000),
          BigDecimal(4000),
          Some(1.5),
          BigDecimal(1000),
          Some("link"),
          ProductType.PLAN,
          ProductStatus.ACTIVE
      )
      info(item.toString)
      val response = dao.Products.insert(item)
      assert(response.id.getOrElse(-1) === 1)
      
      dao.Products.insert(Product(
          None,
          "IPhone4S",
          Some("Also good"),
          DateTime.now,
          None,
          Some("IPHONE4S"),
          BigDecimal(395),
          BigDecimal(3000),
          BigDecimal(4000),
          Some(1.5),
          BigDecimal(1000),
          Some("link"),
          ProductType.PLAN,
          ProductStatus.ACTIVE
      ))
    }
  }
  
  test("Query Product Code") {
    dao.db withSession {
      val item = dao.Products.forProductCode("IPHONE5").get
      info(item.toString())
      assert(item.productCode.get === "IPHONE5")
    }
  }

  test("Fetch Product by Id"){
    dao.db withSession {
      val item = dao.Products.byId(1)
      info(item.toString)
      assert(item.id.get === 1)
    }
  }

  test("Update Product"){
    dao.db withSession {
      val item = dao.Products.byId(1)
      //info(item.toString)
      assert(item.id.get === 1)
      assert(item.productName === "Iphone5")
      dao.Products.update(item.copy(productName="NiceIphone5",productDesn=None))
      val item2 = dao.Products.byId(1)
      info(item2.toString)
      assert(item2.id.get === 1)
      assert(item2.productName === "NiceIphone5")
    }
  }

  test("Query by Product Status and Type"){
    dao.db withSession {
      val item = dao.Products.byId(1)
      assert(item.id.get === 1)
      val items: List[Product] = dao.Products.forProductTypeAndStatus(item.productType.toString,item.productStatus.toString)
      info(items.toString())
      assert(items.size != 0)
    }
  }

  test("Delete Product"){
    dao.db withSession {
      val item = dao.Products.insert(
          Product(
              None,
              "IPhone4S_toDelete",
              Some("Also good"),
              DateTime.now,
              None,
              Some("IPHONE4S_DELETE_TEST"),
              BigDecimal(395.55),
              BigDecimal(3000),
              BigDecimal(4000),
              Some(1.5),
              BigDecimal(1000),
              Some("link"),
              ProductType.PLAN,
              ProductStatus.ACTIVE
          ))
      assert(!item.id.isEmpty)
      val result = dao.Products.deleteById(item.id.get)
      info("deleting result is " + result)
    }
  }
  
  test("Persist User"){
    dao.db withSession {
      val item = User(None, "sillycat", 31, UserType.ADMIN, DateTime.now , DateTime.now, "111111", "admin@gmail.com")
      info(item.toString)
      val id = dao.Users.insert(item)
      assert(id === 1)
    }
  }
  
  test("Fetch User"){
    dao.db withSession {
      val item = dao.Users.get(1)
      info(item.toString)
      assert(item.get.id.get === 1)
      assert(item.get.userName === "sillycat")
    }
  }

  
  test("Persist Cart"){
    dao.db withSession {
      //id: Option[Long], cartName: String, cartType: CartType.Value, user: User, products: Seq[Product]
      val user = dao.Users.get(1).get
      val products = dao.Products.all
      val item = Cart(None, "Sunday Cart", CartType.CHENGDU, user , products)
      info(item.toString)
      val id = dao.Carts.insert(item)
      assert(id === 1)
    }
  }


  
}