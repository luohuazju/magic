package models

/**
 * Customer Information
 */

case class CustomerUser(
    id:Long,
    userId:Long,
    userRate:Long,
    user:User
)

object CustomerUser {

}