package webtanks.actors

import akka.actor._
import play.api.Logger
import shared.{InEvent, PlayerInput, PlayerState, Position}
import upickle.default._
/**
  * Created by Szymon Barańczyk on 2017-02-13.
  */
object WebSocketActor {
  def props(out: ActorRef) = Props(new WebSocketActor(out))
}

class WebSocketActor(out: ActorRef) extends Actor {
  def receive = {
    case msg: String =>
      val input = read[InEvent](msg)
      input match {
        case in: PlayerInput =>
          Logger.debug("msg received " + in)
          out ! write(PlayerState(Position(1, 1, 0), 1, 10, "I"))
      }

  }
}
