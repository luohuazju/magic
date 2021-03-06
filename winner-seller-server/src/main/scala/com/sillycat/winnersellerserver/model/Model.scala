package com.sillycat.winnersellerserver.model

import org.joda.time.DateTime
import scala.slick.direct.AnnotationMapper.column
import com.sillycat.winnersellerserver.dao.Profile
import scala.slick.util.Logging

case class NavBar(id: Option[Long], title: String, link: String, alter: String, parentId: Option[Long], subs: Option[List[NavBar]], parent: Option[NavBar])

case class Cart(id: Option[Long], cartName: String, cartType: CartType.Value, user: User, products: Seq[Product])

case class Product(
  id: Option[Long],
  productName: String,
  productDesn: Option[String],
  createDate: DateTime,
  expirationDate: Option[DateTime],
  productCode: Option[String],
  productPriceUS: BigDecimal,
  productPriceCN: BigDecimal,
  productSellingPriceCN: BigDecimal,
  productWeight: Option[Double],
  productWin: BigDecimal,
  productLink: Option[String],
  productType: ProductType.Value,
  productStatus: ProductStatus.Value)

/**
 * 1 User(1, 'admin', ... , 'admin')
 * 2 User(2, 'manager', ..., 'manager')
 * 3 User(3, 'customer', ..., 'customer')
 */
case class User(
  id: Option[Long],
  userName: String,
  age: Int,
  userType: UserType.Value,
  createDate: DateTime,
  expirationDate: DateTime,
  password: String,
  email: String)

case class UserLogon(
  email: String,
  password: String)

/**
 * 1 Role(1, "admin", "")
 * 2 Role(2, "manager", "")
 * 3 Role(3, "customer", "")
 */
case class Role(id: Option[Long], roleCode: String, description: String)

/**
 * 1. RUserRole(1, 1)
 * 2. RUserRole(2, 2)
 * 3. RUserRole(3, 3)
 */
case class RUserRole(roleId: Long, userId: Long)

case class RCartProduct(cartId: Long, productId: Long)
