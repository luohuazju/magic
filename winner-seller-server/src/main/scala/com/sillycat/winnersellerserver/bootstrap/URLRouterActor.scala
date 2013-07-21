package com.sillycat.winnersellerserver.bootstrap

import akka.actor.{ Actor }

class URLRouterActor extends Actor
  with NavBarRouterService
  with ProductRouterService
  with UserRouterService {

  def actorRefFactory = context
  def receive = runRoute(navBarRoute ~ productRoute ~ userRoute)

}

