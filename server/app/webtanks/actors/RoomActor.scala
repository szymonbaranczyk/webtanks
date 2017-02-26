package webtanks.actors

import akka.actor.{Actor, ActorRef, Props}
import akka.stream.actor.ActorPublisher
import shared.{InEvent, OutEvent, PlayerInput, Shot}
import upickle.default.read

/**
  * Created by Szymon Baranczyk on 26/02/2017.
  */


class RoomActor extends Actor {
  var bullets: Seq[ActorRef] = Seq()
  var players: Seq[ActorRef] = Seq()

  override def receive: Receive = {
    case AddPlayer(out) => val p = context.actorOf(PlayerActor.props(out))
    case AddBullet() => val b = context.actorOf(Props[BulletActor])
    case Step() => context.children.foreach(ref => ref ! Step())
  }
}

case class AddPlayer(out: ActorRef)

case class ReturnPlayerRef(actorRef: ActorRef)

case class AddBullet()

case class ReturnBulletRef(actorRef: ActorRef)

case class Step()

class PlayerActor(out: ActorRef) extends Actor {

  override def receive: Receive = {
    case msg: String =>
      read[InEvent](msg) match {
        case input: PlayerInput =>
        case shot: Shot =>
      }

  }
}

object PlayerActor {
  def props(out: ActorRef) = Props(new PlayerActor(out))
}

class BulletActor extends Actor {

  override def receive: Receive = {
    case _ =>
  }
}

class PlayerPublisher extends ActorPublisher[OutEvent] {
  override def receive: Receive = {
    case _: OutEvent => onNext(_)
  }
}

object PlayerPublisher {
  def props() = Props(new PlayerActor())
}
