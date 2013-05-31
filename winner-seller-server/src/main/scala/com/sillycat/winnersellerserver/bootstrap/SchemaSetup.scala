package com.sillycat.winnersellerserver.bootstrap

import com.sillycat.winnersellerserver.dao.BaseDAO
import com.typesafe.scalalogging.slf4j.Logging


object SchemaSetup extends Logging{

    def initTables(db:String) = {
      val dao: BaseDAO = BaseDAO.apply(db)
      logger.info("Start to build the Tables")
      dao.create
    }

    def initData(db:String) = {
      val dao: BaseDAO = BaseDAO.apply(db)
      logger.info("Start to init the data")
      dao.buildData
    }
}
