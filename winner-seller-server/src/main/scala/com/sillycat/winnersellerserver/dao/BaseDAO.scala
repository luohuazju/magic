package com.sillycat.winnersellerserver.dao

import scala.slick.driver.ExtendedProfile
import scala.slick.driver.H2Driver
import scala.slick.driver.MySQLDriver
import scala.slick.session.Database
import scala.slick.session.Database.threadLocalSession
import scala.slick.session.Session
import com.sillycat.winnersellerserver.util.DBConn
import com.sillycat.winnersellerserver.util.TestDBConn
import scala.slick.jdbc.meta.MTable
import scala.slick.util.Logging
import com.sillycat.winnersellerserver.model.NavBar

class BaseDAO(override val profile: ExtendedProfile, dbConn: DBConn) extends ProductDAO with UserDAO with CartDAO with RCartProductDAO with NavBarDAO with Profile with Logging {

  def db: Database = { dbConn.database }

  def checkTable: Boolean = db withSession {
    val tableList = MTable.getTables.list
    val bool : Boolean = !tableList.isEmpty
    bool
  }

  def create: Unit = db withSession {
    val tableList = MTable.getTables.list(db)
    logger.info("Table we Having: " + tableList.toString())
    if(tableList.isEmpty){
      logger.info("Table " + Users.tableName + " not exist, creating.")
    	Users.create
      logger.info("Table " + Products.tableName + " not exist, creating.")
      Products.create
      logger.info("Table " + Carts.tableName + " not exist, creating.")
      Carts.create
      logger.info("Table " + NavBars.tableName + " not exist, creating.")
      NavBars.create

      logger.info("Table " + RCartProducts.tableName + " not exist, creating.")
      RCartProducts.create
    }
  }

  def buildData: Unit = db withSession {
    val navs = NavBars.all()
    logger.info("NavBar Table we have isEmtpy = " + navs.isEmpty)
    if(navs.isEmpty){
      logger.info("Insert the NavBar Items...")
      val id1 = NavBars.insert(NavBar(None,"首页","#","alter",Some(0), None, None))
      val id2 = NavBars.insert(NavBar(None,"商品管理","#","alter",Some(0), None, None))
      val id3 = NavBars.insert(NavBar(None,"计划商品","#products/productplan","alter",Some(id2), None, None))
      val id4 = NavBars.insert(NavBar(None,"上架商品","#","alter",Some(id2), None, None))
      val id5 = NavBars.insert(NavBar(None,"历史商品","#","alter",Some(id2), None, None))
      val id6 = NavBars.insert(NavBar(None,"divider", "#", "alter", Some(id2), None, None))
      val id7 = NavBars.insert(NavBar(None,"添加商品", "#product/create", "alter", Some(id2), None, None))
      val id8 = NavBars.insert(NavBar(None,"系统介绍","#about","alter",Some(0), None, None))
      logger.info("Insert the NavBar Items End.")
    }
  }

  def checkData: Boolean = db withSession {
      val bool : Boolean = !NavBars.all().isEmpty
      bool
  }

  def drop: Unit = db withSession {
    Users.drop
    Products.drop
    Carts.drop
    NavBars.drop
    
    RCartProducts.drop
  }

  def doWithSession(f: Unit => Unit) = db withSession { f }

}

object BaseDAO {
  def apply: BaseDAO = {
    new BaseDAO(H2Driver, DBConn)
  }

  def apply(s: String): BaseDAO = s match {
    case "test" => new BaseDAO(H2Driver, TestDBConn)
    case "app" => new BaseDAO(H2Driver, DBConn)
    case _ => new BaseDAO(MySQLDriver, DBConn)
  }

  // Clients can import this rather than depending on slick directly
  implicit def threadLocalSession: Session = scala.slick.session.Database.threadLocalSession
}
