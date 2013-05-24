package models

/**
 * Payment Information
 */

case class Payment(
		id:Long,
		fromUserId:Long,
		toUserId:Long,
		fromAccountId:Long,
		toAccountId:Long,
		totalUS:BigDecimal,
		totalCN:BigDecimal,
		rate:BigDecimal,
		paymentType:String,
		cartId:Long
)

object Payment {

}