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
      val item = Product(None, "Iphone5", "Nice device", DateTime.now, DateTime.now, "IPHONE5")
      info(item.toString)
      val id = dao.Products.insert(item)
      assert(id === 1)
      
      dao.Products.insert(new Product(None, "IPhone4S", "Also good", DateTime.now, DateTime.now, "IPHONE4S"))
    }
  }
  
  test("Query Product Code") {
    dao.db withSession {
      val item = dao.Products.forProductCode("IPHONE5").get
      info(item.toString())
      assert(item.productCode === "IPHONE5")
    }
  }
  
  test("Persist User"){
    dao.db withSession {
      val item = User(None, "sillycat", 31, UserType.ADMIN, DateTime.now , DateTime.now, "111111")
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