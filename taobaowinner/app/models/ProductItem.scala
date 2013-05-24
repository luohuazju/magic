package models

/**
 * Product bind other properties
 */

class ProductItem(
		id:Long,
		productId:Long,
		productName:String,
		productDesn:String,
		productOriginalPriceUS:BigDecimal,
		productOriginalPriceCN:BigDecimal,
		productBenifitPriceUS:BigDecimal,
		productBenifitPriceCN:BigDecimal,
		productCostPriceUS:BigDecimal,
		productCostPriceCN:BigDecimal,
		productSellingPriceUS:BigDecimal,
		productSellingPriceCN:BigDecimal,
		rate:BigDecimal,
		productLink:String,
		productSellingLink:String,
		disCount:BigDecimal,
		disCountAdminId:Long,
		cartId:Long,
		shippingCartId:Long,
		num:Long,
		operatorId:Long,
		ownerId:Long,
		status:String,
		product:Product
)

object ProductItem {

}