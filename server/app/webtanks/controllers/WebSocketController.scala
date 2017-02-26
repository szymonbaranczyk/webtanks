package webtanks.controllers

import javax.inject.Inject

import akka.actor.{ActorSystem, Props}
import akka.pattern.ask
import akka.stream.Materializer
import akka.stream.scaladsl.{Flow, Sink, Source}
import play.api.mvc._
import shared.OutEvent
import upickle.default._
import webtanks.actors._

import scala.concurrent.Await
import scala.concurrent.duration._
/**
  * Created by Szymon Barańczyk on 2017-02-13.
  */
class WebSocketController @Inject() (implicit system: ActorSystem, materializer: Materializer)  extends Controller {
  val room = system.actorOf(Props[RoomActor])
  def socket: WebSocket = WebSocket.accept[String, String] {
    request =>
      val publisher = PlayerPublisher.props()
      val dataSource = Source.actorPublisher[OutEvent](publisher)
      val player = Await.result((room ? AddPlayer(publisher)).mapTo[ReturnPlayerRef], 1 second).actorRef
      val sink = Sink.foreach((json: String) => player ! json)
      Flow.fromSinkAndSource(sink,
        dataSource map { d => write(d) })
  }
}
