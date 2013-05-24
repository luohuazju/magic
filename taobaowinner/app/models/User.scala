package models

/**
 * Basic User Information
 */

case class User(
    id: Long, 
    email: String, 
    nickName: String,
    password: String, 
    phone: String, 
    amount: BigDecimal,
    itemBuy: Long,
    itemSell: Long,
    totolBuyUS: BigDecimal,
    totolSellUS: BigDecimal,
    roles: List[UserRole],
    accounts: List[Account]
)

object User {

}