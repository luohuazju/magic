package models

/**
 * Account Information
 */

case class Account(
    id: Long, 
    accountName: String, 
    accountNumber: String, 
    cash:BigDecimal, 
    userId: Long, 
    accountType:String,
    operatorId:Long
)

object Account {

}