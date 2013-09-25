package com.sillycat.winnersellerserver

import akka.actor.{ ActorRefFactory, ActorSystem, Props }
import com.sillycat.winnersellerserver.bootstrap.URLRouterActor
import com.sillycat.winnersellerserver.bootstrap.Bootstrap
import spray.can.Http
import akka.io.IO
import scala.concurrent.ExecutionContext

object Boot extends App with Bootstrap {
  implicit val system = ActorSystem()

  val handler = system.actorOf(Props[URLRouterActor])

  IO(Http) ! Http.Bind(handler, interface = serverAddress, port = serverPort)

}