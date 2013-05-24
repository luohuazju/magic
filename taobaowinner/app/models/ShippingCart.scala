package models

/**
 * shipping cart Information
 */

case class ShippingCart(
		id:Long,
		fromUserId:Long,
		toUserId:Long,
		fromAddressId:Long,
		toAddressId:Long,
		status:String,
		shippingFeeUS:BigDecimal,
		shippingFeeCN:BigDecimal,
		rate:BigDecimal,
		operatorId:Long,
		productItems:List[ProductItem]
)

object ShippingCart {

}