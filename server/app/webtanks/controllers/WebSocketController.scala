package webtanks.controllers

import javax.inject.Inject

import akka.actor.ActorSystem
import akka.stream.Materializer
import play.api._
import play.api.libs.json.JsValue
import play.api.libs.streams._
import play.api.mvc.WebSocket.MessageFlowTransformer
import play.api.mvc._
import webtanks.actors.WebSocketActor
/**
  * Created by Szymon Barańczyk on 2017-02-13.
  */
class WebSocketController @Inject() (implicit system: ActorSystem, materializer: Materializer)  extends Controller {

//  import play.api.libs.json._
//
//  implicit private val inEventFormat = Json.format[InEvent]
//  implicit private val outEventFormat = Json.format[OutEvent]
//
//  import play.api.mvc.WebSocket.FrameFormatter
//
//  implicit val messageFlowTransformer = MessageFlowTransformer.jsonMessageFlowTransformer[InEvent, OutEvent]

  def socket: WebSocket = WebSocket.accept[String, String] { request =>
    ActorFlow.actorRef(out => WebSocketActor.props(out))
  }
}
