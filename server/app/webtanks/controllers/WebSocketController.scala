package webtanks.controllers

import javax.inject.Inject

import akka.actor.ActorSystem
import akka.stream.Materializer
import play.api.libs.streams._
import play.api.mvc._
import webtanks.actors.WebSocketActor
/**
  * Created by Szymon Barańczyk on 2017-02-13.
  */
class WebSocketController @Inject() (implicit system: ActorSystem, materializer: Materializer)  extends Controller {

  def socket: WebSocket = WebSocket.accept[String, String] {
    request =>
      ActorFlow.actorRef(out => WebSocketActor.props(out))
  }
}
