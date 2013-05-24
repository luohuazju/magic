package models

/**
 * event cash message
 */
case class EventCash(
    id:Long, 
    eventId:Long, 
    fromAccountId:Long, 
    toAccountId:Long, 
    fromUserId: Long, 
    toUserId:Long,
    totalUS:BigDecimal,
    totalCN:BigDecimal,
    rate:BigDecimal
    )

object EventCash {

}