package com.sillycat.winnersellerserver.model

object ProductType extends Enumeration {
  type ProductType = Value
  val PLAN, ONLINE, HISTORY = Value
}

object ProductStatus extends Enumeration {
  type ProductStatus = Value
  val ACTIVE, DISABLE = Value
}

object UserType extends Enumeration {
  type UserType = Value
  val ADMIN, CUSTOMER, SELLER = Value
}

object CartType extends Enumeration {
  type CartType = Value
  val DIRECT, CHENGDU = Value
}

object DBType extends Enumeration {
  type DBType = Value
  val H2, MySQL = Value
}