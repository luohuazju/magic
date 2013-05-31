package com.sillycat.winnersellerserver.dao

import com.sillycat.winnersellerserver.model.Product
import com.sillycat.winnersellerserver.model.Cart
import com.sillycat.winnersellerserver.model.RCartProduct
import com.sillycat.winnersellerserver.model.User
import com.sillycat.winnersellerserver.model.UserType
import org.joda.time.DateTime
import scala.slick.util.Logging
import scala.slick.jdbc.meta.MTable
import com.sillycat.winnersellerserver.model.NavBar

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
        { (s: NavBar) => Some(s.id ,s.title, s.link, s.alter, s.parentId) })

    def forInsert = title ~ link ~ alter ~ parentId.? <>
      ({ t => NavBar(None, t._1, t._2, t._3, t._4, None, None) },
        { (s: NavBar) => Some(s.title, s.link, s.alter, s.parentId) })


    def insert(s: NavBar)(implicit session: Session): Long = {
      NavBars.forInsert returning id insert s
    }

    def all()(implicit session: Session): List[NavBar] = {
      val s1 = Query(NavBars).list
      val s2 = s1.filter(_.parentId.getOrElse(-1) == 0)   //filter to get the root NavBars

      def placeSubandParent(item: NavBar, all :List[NavBar]) : NavBar = {
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

  object Users extends Table[(Long, String, Int, String, DateTime, DateTime, String)]("USER") {
    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc) // 1 This is the primary key column   
    def userName = column[String]("USER_NAME") // 2
    def age = column[Int]("AGE") //3
    def userType = column[String]("USER_TYPE") //4
    def createDate = column[DateTime]("CREATE_DATE") //5
    def expirationDate = column[DateTime]("EXPIRATION_DATE") // 6
    def password = column[String]("PASSWORD") // 7

    def * = id ~ userName ~ age ~ userType ~ createDate ~ expirationDate ~ password

    def persist(implicit session: Session) = id.? ~ userName ~ age ~ userType ~ createDate ~ expirationDate ~ password

    def insert(user: User)(implicit session: Session): Long = {
      val id = persist.insert(user.id, user.userName, user.age, user.userType.toString(), user.createDate, user.expirationDate, user.password)
      id
    }

    def auth(userName: String, password: String)(implicit session: Session): Option[User] = {
      logger.debug("I am authing the userName=" + userName + " password=" + password)
      (userName, password) match {
        case ("admin", "admin") =>
          Option(User(Some(1), "admin", 100, UserType.ADMIN, new DateTime(), new DateTime(), "admin"))
        case ("customer", "customer") =>
          Option(User(Some(2), "customer", 100, UserType.CUSTOMER, new DateTime(), new DateTime(), "customer"))
        case ("manager", "manager") =>
          Option(User(Some(3), "manager", 100, UserType.SELLER, new DateTime(), new DateTime(), "manager"))
        case _ => None
      }
    }
    
    def get(userId: Long)(implicit session: Session): Option[User] = {
      logger.debug("Try to fetch User Object with userId = " + userId)
      val query = for { item <- Users if (item.id === userId) } yield (item)
      logger.debug("Get User by id, SQL should be : " + query.selectStatement)
      query.firstOption map {
        case (user) => User(Option(user._1), user._2, user._3, UserType.withName(user._4), user._5, user._6, user._7)
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

  object Products extends Table[Product]("PRODUCT") {
    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc) // 1 This is the primary key column   
    def productName = column[String]("PRODUCT_NAME") // 2
    def productDesn = column[String]("PRODUCT_DESN") //3
    def createDate = column[DateTime]("CREATE_DATE") //4
    def expirationDate = column[DateTime]("EXPIRATION_DATE") // 5
    def productCode = column[String]("PRODUCT_CODE") //6

    def * = id.? ~ productName ~ productDesn ~ createDate ~ expirationDate ~ productCode <> (Product.apply _, Product.unapply _)

    def forInsert = productName ~ productDesn ~ createDate ~ expirationDate ~ productCode <>
      ({ t => Product(None, t._1, t._2, t._3, t._4, t._5) },
        { (s: Product) => Some(s.productName, s.productDesn, s.createDate, s.expirationDate, s.productCode) })

    def insert(s: Product)(implicit session: Session): Long = {
      Products.forInsert returning id insert s
    }

    def forProductCode(productCode: String)(implicit session: Session): Option[Product] = {
      val query = for {
        item <- Products if item.productCode === productCode
      } yield item
      query.firstOption
    }
    
    def all()(implicit session: Session): Seq[Product] = {
      Query(Products).list
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
