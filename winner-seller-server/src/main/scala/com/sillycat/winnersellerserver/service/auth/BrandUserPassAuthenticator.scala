package com.sillycat.winnersellerserver.service.auth

import spray.routing.authentication._
import scala.concurrent.{ Promise, Future }
import scala.slick.session.Database.threadLocalSession
import com.sillycat.winnersellerserver.dao.BaseDAO
import com.sillycat.winnersellerserver.model.User
import com.typesafe.scalalogging.slf4j.Logging

class BrandUserPassAuthenticator(dao: BaseDAO) extends UserPassAuthenticator[User] with Logging {

  def apply(userPass: Option[UserPass]) =
    Future.successful(
      userPass match {
        case Some(UserPass(user, pass)) => {
          dao.db withSession {
            dao.Users.getForEmail(user)
          } match {
              case Some(x) => {
                if (x.password == pass) {
                  logger.info("working, I get the right user and pass.")
                  Some(x)
                } else {
                  logger.info("password fail.")
                  None
                }
              }
              case _ => {
                logger.info("not hitting the right user")
                None
              }
            }
        }
        case _ => {
          logger.info("no user, no pass.");
          None
        }
      })

}
