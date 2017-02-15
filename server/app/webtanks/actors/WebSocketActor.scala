package webtanks.actors

import akka.actor._
import play.api.libs.json.JsValue
import play.api.Logger
/**
  * Created by Szymon Barańczyk on 2017-02-13.
  */
object WebSocketActor {
  def props(out: ActorRef) = Props(new WebSocketActor(out))
}

class WebSocketActor(out: ActorRef) extends Actor {
  def receive = {
    case msg: JsValue =>
      Logger.debug("msg reveived")
      out ! msg
  }
}
