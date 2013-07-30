package com.sillycat.winnersellerserver.dao

import com.sillycat.winnersellerserver.model.Product
import com.sillycat.winnersellerserver.model.Cart
import com.sillycat.winnersellerserver.model.RCartProduct
import com.sillycat.winnersellerserver.model.User
import com.sillycat.winnersellerserver.model.UserType
import com.sillycat.winnersellerserver.model.ProductStatus
import com.sillycat.winnersellerserver.model.ProductType
import org.joda.time.DateTime
import scala.slick.util.Logging
import scala.slick.jdbc.meta.MTable
import com.sillycat.winnersellerserver.model.NavBar
import scala.slick.jdbc.{ SetParameter, GetResult, StaticQuery }
import com.sillycat.winnersellerserver.util.JodaTimestampMapper
import com.sillycat.winnersellerserver.util.JodaTimestampOptionMapper
import com.sillycat.winnersellerserver.util.SillycatConstant
import scala.slick.session.PositionedParameters
import java.sql.Timestamp
import org.joda.time.DateTime
import scala.slick.util.Logging
import org.joda.time.format.DateTimeFormat
import scala.slick.session.PositionedParameters
import java.sql.{ Date, Timestamp }

//case class NavBar(id: Option[Long], title: String, link: String, alter: String, subs: Seq[NavBar] , parent: NavBar)
trait NavBarDAO extends Logging { this: Profile =>
  import profile.simple._

  object NavBars extends Table[NavBar]("NAVBAR") {
    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc) // 1 This is the primary key column
    def title = column[String]("NAVBAR_TITLE") // 2
    def link = column[String]("NAVBAR_LINK") //3
    def alter = column[String]("NAVBAR_ALTER") //4
    def parentId = column[Long]("PARENT_ID") //5

    def * = id.? ~ title ~ link ~ alter ~ parentId.? <>
      ({ t => NavBar(t._1, t._2, t._3, t._4, t._5, None, None) },
        { (s: NavBar) => Some(s.id, s.title, s.link, s.alter, s.parentId) })

    def forInsert = title ~ link ~ alter ~ parentId.? <>
      ({ t => NavBar(None, t._1, t._2, t._3, t._4, None, None) },
        { (s: NavBar) => Some(s.title, s.link, s.alter, s.parentId) })

    def insert(s: NavBar)(implicit session: Session): Long = {
      NavBars.forInsert returning id insert s
    }

    def all()(implicit session: Session): List[NavBar] = {
      val s1 = Query(NavBars).list
      val s2 = s1.filter(_.parentId.getOrElse(-1) == 0) //filter to get the root NavBars

      def placeSubandParent(item: NavBar, all: List[NavBar]): NavBar = {
        val nav = NavBar(
          item.id,
          item.title,
          item.link,
          item.alter,
          item.parentId,
          Option(all.filter(_.parentId.getOrElse(-1) == item.id.getOrElse(0))),
          all.filter(_.id.getOrElse(0) == item.parentId.getOrElse(-1)).headOption
        )
        nav
      }

      val s3 = s2.map(placeSubandParent(_, s1))
      s3
    }

    def create(implicit session: Session) = {
      if (!MTable.getTables(this.tableName).firstOption.isDefined) {
        val ddl = this.ddl
        ddl.create
        //ddl.createStatements.foreach(println)
      }
    }

    def drop(implicit session: Session) = {
      if (MTable.getTables(this.tableName).firstOption.isDefined) {
        val ddl = this.ddl
        ddl.drop
        //ddl.dropStatements.foreach(println)
      }
    }
  }
}

trait UserDAO extends Logging { this: Profile =>
  import profile.simple._

  object Users extends Table[(Long, String, Int, String, DateTime, DateTime, String, String)]("USER") {
    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc) // 1 This is the primary key column   
    def userName = column[String]("USER_NAME") // 2
    def age = column[Int]("AGE") //3
    def userType = column[String]("USER_TYPE") //4
    def createDate = column[DateTime]("CREATE_DATE") //5
    def expirationDate = column[DateTime]("EXPIRATION_DATE") // 6
    def password = column[String]("PASSWORD") // 7
    def email = column[String]("EMAIL") //8

    def * = id ~ userName ~ age ~ userType ~ createDate ~ expirationDate ~ password ~ email

    def persist(implicit session: Session) = id.? ~ userName ~ age ~ userType ~ createDate ~ expirationDate ~ password ~ email

    def insert(user: User)(implicit session: Session): Long = {
      val id = persist.insert(user.id, user.userName, user.age, user.userType.toString(), user.createDate, user.expirationDate, user.password, user.email)
      id
    }

    def auth(email: String, password: String)(implicit session: Session): Option[User] = {
      logger.debug("I am auth the userName=" + email + " password=" + password)
      (email, password) match {
        case ("admin@gmail.com", "admin") =>
          Option(User(Some(1), "admin", 100, UserType.ADMIN, new DateTime(), new DateTime(), "admin", "admin@gmail.com"))
        case ("admin", "admin") =>
          Option(User(Some(1), "admin", 100, UserType.ADMIN, new DateTime(), new DateTime(), "admin", "admin@gmail.com"))
        case ("customer@gmail.com", "customer") =>
          Option(User(Some(2), "customer", 100, UserType.CUSTOMER, new DateTime(), new DateTime(), "customer", "customer@gmail.com"))
        case ("customer", "customer") =>
          Option(User(Some(2), "customer", 100, UserType.CUSTOMER, new DateTime(), new DateTime(), "customer", "customer@gmail.com"))
        case ("manager", "manager") =>
          Option(User(Some(3), "manager", 100, UserType.SELLER, new DateTime(), new DateTime(), "manager", "manager@gmail.com"))
        case ("manager@gmail.com", "manager") =>
          Option(User(Some(3), "manager", 100, UserType.SELLER, new DateTime(), new DateTime(), "manager", "manager@gmail.com"))
        case _ => None
      }
    }

    def getForEmail(email: String)(implicit session: Session): Option[User] = {
      logger.debug("I am auth the userName=" + email)
      (email) match {
        case ("admin@gmail.com") =>
          Option(User(Some(1), "admin", 100, UserType.ADMIN, new DateTime(), new DateTime(), "admin", "admin@gmail.com"))
        case ("admin") =>
          Option(User(Some(1), "admin", 100, UserType.ADMIN, new DateTime(), new DateTime(), "admin", "admin@gmail.com"))
        case ("customer@gmail.com") =>
          Option(User(Some(2), "customer", 100, UserType.CUSTOMER, new DateTime(), new DateTime(), "customer", "customer@gmail.com"))
        case ("customer") =>
          Option(User(Some(2), "customer", 100, UserType.CUSTOMER, new DateTime(), new DateTime(), "customer", "customer@gmail.com"))
        case ("manager") =>
          Option(User(Some(3), "manager", 100, UserType.SELLER, new DateTime(), new DateTime(), "manager", "manager@gmail.com"))
        case ("manager@gmail.com") =>
          Option(User(Some(3), "manager", 100, UserType.SELLER, new DateTime(), new DateTime(), "manager", "manager@gmail.com"))
        case _ => None
      }
    }

    def get(userId: Long)(implicit session: Session): Option[User] = {
      logger.debug("Try to fetch User Object with userId = " + userId)
      val query = for { item <- Users if (item.id === userId) } yield (item)
      logger.debug("Get User by id, SQL should be : " + query.selectStatement)
      query.firstOption map {
        case (user) => User(Option(user._1), user._2, user._3, UserType.withName(user._4), user._5, user._6, user._7, user._8)
      }
    }

    def create(implicit session: Session) = {
      if (!MTable.getTables(this.tableName).firstOption.isDefined) {
        val ddl = this.ddl
        ddl.create
        //ddl.createStatements.foreach(println)
      }
    }

    def drop(implicit session: Session) = {
      if (MTable.getTables(this.tableName).firstOption.isDefined) {
        val ddl = this.ddl
        ddl.drop
        //ddl.dropStatements.foreach(println)
      }
    }
  }
}

trait ProductDAO extends Logging { this: Profile =>
  import profile.simple._

  implicit object SetDateTime extends SetParameter[DateTime] { def apply(v: DateTime, pp: PositionedParameters) { pp.setTimestamp(new Timestamp(v.getMillis)) } }

  object Products extends Table[(Option[Long], String, Option[String], DateTime, Option[DateTime], Option[String], BigDecimal, BigDecimal, BigDecimal, Option[Double], BigDecimal, Option[String], String, String)]("PRODUCT") {
    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc) // 1 This is the primary key column   
    def productName = column[String]("PRODUCT_NAME") // 2
    def productDesn = column[String]("PRODUCT_DESN", O.Nullable) //3
    def createDate = column[DateTime]("CREATE_DATE") //4
    def expirationDate = column[DateTime]("EXPIRATION_DATE", O.Nullable) // 5
    def productCode = column[String]("PRODUCT_CODE", O.Nullable) //6
    def productPriceUS = column[BigDecimal]("PRODUCT_PRICE_US", O.DBType("decimal(10, 4)")) //7
    def productPriceCN = column[BigDecimal]("PRODUCT_PRICE_CN", O.DBType("decimal(10, 4)")) //8
    def productSellingPriceCN = column[BigDecimal]("PRODUCT_SELLING_PRICE_CN", O.DBType("decimal(10, 4)")) //9
    def productWeight = column[Double]("PRODUCT_WEIGHT", O.Nullable) //10
    def productWin = column[BigDecimal]("PRODUCT_WIN", O.DBType("decimal(10, 4)")) //11
    def productLink = column[String]("PRODUCT_LINK", O.Nullable) //12
    def productType = column[String]("PRODUCT_TYPE") //13
    def productStatus = column[String]("PRODUCT_STATUS") //14

    def * = id.? ~ //1
      productName ~ //2
      productDesn.? ~ //3
      createDate ~ //4
      expirationDate.? ~ //5
      productCode.? ~ //6
      productPriceUS ~ //7
      productPriceCN ~ //8
      productSellingPriceCN ~ //9
      productWeight.? ~ //10
      productWin ~ //11
      productLink.? ~ //12
      productType ~ //13
      productStatus //14

    def persist(implicit session: Session) =
      id.? ~ //1
        productName ~ //2
        productDesn.? ~ //3
        createDate ~ //4
        expirationDate.? ~ //5
        productCode.? ~ //6
        productPriceUS ~ //7
        productPriceCN ~ //8
        productSellingPriceCN ~ //9
        productWeight.? ~ //10
        productWin ~ //11
        productLink.? ~ //12
        productType ~ //13
        productStatus //14

    def insert(item: Product)(implicit session: Session): Product = {
      val id = persist.insert(
        item.id, //1
        item.productName, //2
        item.productDesn, //3
        item.createDate, //4
        item.expirationDate, //5
        item.productCode, //6
        item.productPriceUS, //7
        item.productPriceCN, //8
        item.productSellingPriceCN, //9
        item.productWeight, //10
        item.productWin, //11
        item.productLink, //12
        item.productType.toString, //13
        item.productStatus.toString) //14
      val lastInsertedId: Long = StaticQuery.queryNA[Long]("SELECT LAST_INSERT_ID()").first
      logger.info("I got the returnId as " + lastInsertedId)
      byId(lastInsertedId)

    }

    implicit val getProductResult =
      GetResult(
        r => new Product(
          id = r.nextLongOption, //1
          productName = r.nextString(), //2
          productDesn = r.nextStringOption(), //3
          createDate = JodaTimestampMapper.comap(r.nextTimestamp), //4
          expirationDate = JodaTimestampOptionMapper.comap(r.nextTimestampOption()), //5
          productCode = r.nextStringOption(), //6
          productPriceUS = r.nextBigDecimal(), //7
          productPriceCN = r.nextBigDecimal(), //8
          productSellingPriceCN = r.nextBigDecimal(), //9
          productWeight = r.nextDoubleOption(), //10
          productWin = r.nextBigDecimal(), //11
          productLink = r.nextStringOption(), //12
          productType = ProductType.withName(r.nextString()), //13
          productStatus = ProductStatus.withName(r.nextString()) //14
        )
      )

    def update(item: Product)(implicit session: Session): Product = {
      session.withTransaction {

        (StaticQuery.u +
          "update PRODUCT set" +
          "  PRODUCT_NAME = " +? item.productName +
          ", PRODUCT_DESN = " +? item.productDesn +
          ", CREATE_DATE = " +? SillycatConstant.DATE_TIME_FORMAT_1.print(item.createDate) +
          ", EXPIRATION_DATE = " +? SillycatConstant.DATE_TIME_FORMAT_1.print(item.expirationDate.getOrElse(null)) +
          ", PRODUCT_CODE = " +? item.productCode +
          ", PRODUCT_PRICE_US = " +? item.productPriceUS +
          ", PRODUCT_PRICE_CN = " +? item.productPriceCN +
          ", PRODUCT_SELLING_PRICE_CN = " +? item.productSellingPriceCN +
          ", PRODUCT_WEIGHT = " +? item.productWeight +
          ", PRODUCT_WIN = " +? item.productWin +
          ", PRODUCT_LINK = " +? item.productLink +
          ", PRODUCT_TYPE = " +? item.productType.toString +
          ", PRODUCT_STATUS = " +? item.productStatus.toString +
          " where id = " +? item.id
        ).execute

        byId(item.id.get)
      }
    }

    def deleteById(id: Long)(implicit session: Session): Int = {
      val query = for {
        item <- Products if item.id === id
      } yield item
      query.delete
    }

    def forProductCode(productCode: String)(implicit session: Session): Option[Product] = {
      val query = for {
        item <- Products if item.productCode === productCode
      } yield item
      query.firstOption map {
        case (item) => Product(
          item._1,
          item._2,
          item._3,
          item._4,
          item._5,
          item._6,
          item._7,
          item._8,
          item._9,
          item._10,
          item._11,
          item._12,
          ProductType.withName(item._13),
          ProductStatus.withName(item._14))
      }
    }

    def forProductTypeAndStatus(productType: String, productStatus: String)(implicit session: Session): List[Product] = {
      val query = for {
        item <- Products if item.productType === productType && item.productStatus === productStatus
      } yield item
      query.list map {
        case (item) => Product(
          item._1,
          item._2,
          item._3,
          item._4,
          item._5,
          item._6,
          item._7,
          item._8,
          item._9,
          item._10,
          item._11,
          item._12,
          ProductType.withName(item._13),
          ProductStatus.withName(item._14))
      }
    }

    def all()(implicit session: Session): List[Product] = {
      Query(Products).list map {
        case (item) => Product(
          item._1,
          item._2,
          item._3,
          item._4,
          item._5,
          item._6,
          item._7,
          item._8,
          item._9,
          item._10,
          item._11,
          item._12,
          ProductType.withName(item._13),
          ProductStatus.withName(item._14)
        )
      }
    }

    def byId(id: Long)(implicit session: Session): Product = {
      val product: Option[Product] = exits(id)
      require(
        product match {
          case Some(_) => true
          case None => false
        },
        "No product with this id, id = " + id
      )
      product.get
    }

    def exits(id: Long)(implicit session: Session): Option[Product] = {
      val get = StaticQuery[(Long), Product] +
        """
          |select
          | ID,
          | PRODUCT_NAME,
          | PRODUCT_DESN,
          | CREATE_DATE,
          | EXPIRATION_DATE,
          | PRODUCT_CODE,
          | PRODUCT_PRICE_US,
          | PRODUCT_PRICE_CN,
          | PRODUCT_SELLING_PRICE_CN,
          | PRODUCT_WEIGHT,
          | PRODUCT_WIN,
          | PRODUCT_LINK,
          | PRODUCT_TYPE,
          | PRODUCT_STATUS
          |from
          | PRODUCT
          |where
          | ID = ?
        """.stripMargin
      get(id).firstOption
    }

    def create(implicit session: Session) = {
      if (!MTable.getTables(this.tableName).firstOption.isDefined) {
        val ddl = this.ddl
        ddl.create
        //ddl.createStatements.foreach(println)
      }
    }

    def drop(implicit session: Session) = {
      if (MTable.getTables(this.tableName).firstOption.isDefined) {
        val ddl = this.ddl
        ddl.drop
        //ddl.dropStatements.foreach(println)
      }
    }
  }
}

//Cart(id: Option[Long], cartName: String, cartType: CartType.Value, user: User, products: Seq[Product])
trait CartDAO extends Logging { this: Profile with RCartProductDAO =>
  import profile.simple._

  object Carts extends Table[(Long, String, String, Long)]("CART") {
    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc) // 1 This is the primary key column   
    def cartName = column[String]("CART_NAME") // 2
    def cartType = column[String]("CART_TYPE") //3
    def userId = column[Long]("USER_ID") //5

    def * = id ~ cartName ~ cartType ~ userId

    def persist(implicit session: Session) = id.? ~ cartName ~ cartType ~ userId.?

    def insert(cart: Cart)(implicit session: Session): Long = {
      val cartId = persist.insert(cart.id, cart.cartName, cart.cartType.toString(), cart.user.id)
      RCartProducts.insertAll(cartId, cart.products)
      cartId
    }

    def create(implicit session: Session) = {
      if (!MTable.getTables(this.tableName).firstOption.isDefined) {
        val ddl = this.ddl
        ddl.create
        //ddl.createStatements.foreach(println)
      }
    }

    def drop(implicit session: Session) = {
      if (MTable.getTables(this.tableName).firstOption.isDefined) {
        val ddl = this.ddl
        ddl.drop
        //ddl.dropStatements.foreach(println)
      }
    }
  }
}

trait RCartProductDAO extends Logging { this: Profile =>
  import profile.simple._

  object RCartProducts extends Table[RCartProduct]("R_CART_PRODUCT") {
    def cartId = column[Long]("CART_ID", O.NotNull) // 1 This is the primary key column   
    def productId = column[Long]("PRODUCT_ID", O.NotNull) //2

    def * = cartId ~ productId <> (RCartProduct.apply _, RCartProduct.unapply _)

    def insertAll(cartId: Long, productIds: Seq[Product])(implicit session: Session): Unit = {
      productIds.foreach(x => RCartProducts.insert(RCartProduct(cartId, x.id.get)))
    }

    def create(implicit session: Session) = {
      if (!MTable.getTables(this.tableName).firstOption.isDefined) {
        val ddl = this.ddl
        ddl.create
        //ddl.createStatements.foreach(println)
      }
    }

    def drop(implicit session: Session) = {
      if (MTable.getTables(this.tableName).firstOption.isDefined) {
        val ddl = this.ddl
        ddl.drop
        //ddl.dropStatements.foreach(println)
      }
    }
  }
}

