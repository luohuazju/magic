package models

import java.util.Date

/**
 * Shopping Cart
 */

case class Cart(
    id: Long, 
    createDate: Date, 
    updateDate: Date, 
    totalUS: BigDecimal,
    totalCN: BigDecimal,
    rate: BigDecimal,
    userId: Long,
    operatorId:Long,
    productItems:List[ProductItem]
)

object Cart {

}