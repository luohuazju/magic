package com.sillycat.winnersellerserver

import spray.can.server.SprayCanHttpServerApp
import akka.actor.Props
import com.sillycat.winnersellerserver.bootstrap.URLRouterActor
import com.sillycat.winnersellerserver.bootstrap.Bootstrap

object Boot extends App with Bootstrap with SprayCanHttpServerApp {

  val handler = system.actorOf(Props[URLRouterActor])

  newHttpServer(handler) ! Bind(interface = serverAddress, port = serverPort)

}