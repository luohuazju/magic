package com.sillycat.winnersellerserver.dao

import scala.slick.driver.ExtendedProfile
import com.sillycat.winnersellerserver.util.JodaDateTimeMapper
import com.sillycat.winnersellerserver.util.UserTypeMapper
import com.sillycat.winnersellerserver.util.JodaTimestampMapper

trait Profile {
  val profile: ExtendedProfile
  //implicit val jodaMapper = JodaDateTimeMapper
  implicit val jodaTimestampMapper = JodaTimestampMapper
  implicit val userTypeMapper = UserTypeMapper
}