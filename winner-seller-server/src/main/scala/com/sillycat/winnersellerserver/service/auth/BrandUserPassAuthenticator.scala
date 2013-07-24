package com.sillycat.winnersellerserver.service.auth

import spray.routing.authentication._
import scala.concurrent.{ Promise, Future }
import scala.slick.session.Database.threadLocalSession
import com.sillycat.winnersellerserver.dao.BaseDAO
import com.sillycat.winnersellerserver.model.User

class BrandUserPassAuthenticator(dao: BaseDAO) extends UserPassAuthenticator[User] {

  def apply(userPass: Option[UserPass]) =
    Future.successful(
      userPass match {
        case Some(UserPass(user, pass)) => {
          dao.db withSession {
            dao.Users.auth(user, pass) }.flatMap {
              case x => {
                if (x.password == pass) {
                  Some(x)
                } else { None }
              }
          }
        }
        case _ => None
      })

}
