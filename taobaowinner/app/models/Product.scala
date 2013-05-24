package models

/**
 * Original Product
 */

case class Product(
		id:Long,
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
		operatorId:Long
)

object Product {

}