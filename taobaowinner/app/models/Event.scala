package models

import java.util.Date

/**
 * event message
 */
case class Event(
    id: Long, 
    eventDate: Date, 
    operatorId: Long, 
    actionType: String, 
    actionMessage: String
    )

object Event {

}