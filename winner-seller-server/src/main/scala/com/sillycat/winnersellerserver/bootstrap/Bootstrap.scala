package com.sillycat.winnersellerserver.bootstrap

import com.typesafe.config.ConfigFactory

trait Bootstrap {

  val config = ConfigFactory.load()

  val env = config.getString("build.env")
  var serverAddress: String = config.getString("server.address")
  var serverPort: Int = config.getInt("server.port")

  if (env != null && env != "") {
    serverAddress = if (config.getString("environment." + env + ".server.address") != null) config.getString("environment." + env + ".server.address") else serverAddress
    serverPort = if (config.getInt("environment." + env + ".server.port") != 0) config.getInt("environment." + env + ".server.port") else serverPort
  }

  SchemaSetup.initTables("app")
  SchemaSetup.initData("app")

}