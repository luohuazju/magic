package com.sillycat.winnersellerserver.util

import java.sql.Date
import java.sql.Timestamp
import scala.slick.lifted.BaseTypeMapper
import scala.slick.lifted.MappedTypeMapper
import org.joda.time.DateTime
import com.sillycat.winnersellerserver.model.UserType
import com.sillycat.winnersellerserver.model.ProductType
import com.sillycat.winnersellerserver.model.ProductStatus

object JodaTimestampMapper extends MappedTypeMapper[DateTime, Timestamp] with BaseTypeMapper[DateTime] {
  def map(j: DateTime) = new Timestamp(j.getMillis)
  def comap(s: Timestamp) = new DateTime(s.getTime)
  override def sqlType = Some(java.sql.Types.TIMESTAMP)
  override def sqlTypeName = Some("timestamp")
}

object JodaTimestampOptionMapper extends MappedTypeMapper[Option[DateTime], Option[Timestamp]] with BaseTypeMapper[Option[DateTime]] {
  def map(j: Option[DateTime]) = j match {
    case None => None
    case _ => Option(new Timestamp(j.get.getMillis))
  }
  def comap(s: Option[Timestamp]) = s match {
    case None => None
    case _ => Option(new DateTime(s.get.getTime))
  }
  override def sqlType = Some(java.sql.Types.TIMESTAMP)
  override def sqlTypeName = Some("timestamp")
}

//object JodaDateTimeMapper extends MappedTypeMapper[DateTime, Date] with BaseTypeMapper[DateTime] {
//  def map(j: DateTime) = new Date(j.getMillis)
//  def comap(s: Date) = new DateTime(s.getTime)
//  override def sqlType = Some(java.sql.Types.DATE)
//  override def sqlTypeName = Some("date")
//}

object UserTypeMapper extends MappedTypeMapper[UserType.Value, String] with BaseTypeMapper[UserType.Value] {
  def map(j: UserType.Value) = j.toString()
  def comap(s: String) = UserType.withName(s)
  override def sqlType = Some(java.sql.Types.NCHAR)
  override def sqlTypeName = Some("userType")
}

object ProductTypeMapper extends MappedTypeMapper[ProductType.Value, String] with BaseTypeMapper[ProductType.Value] {
  def map(j: ProductType.Value) = j.toString()
  def comap(s: String) = ProductType.withName(s)
  override def sqlType = Some(java.sql.Types.NCHAR)
  override def sqlTypeName = Some("productType")
}

object ProductStatusMapper extends MappedTypeMapper[ProductStatus.Value, String] with BaseTypeMapper[ProductStatus.Value] {
  def map(j: ProductStatus.Value) = j.toString()
  def comap(s: String) = ProductStatus.withName(s)
  override def sqlType = Some(java.sql.Types.NCHAR)
  override def sqlTypeName = Some("productStatus")
}