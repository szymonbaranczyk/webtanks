package shared

import scala.math._
/**
  * Created by Szymon Barańczyk on 2017-02-13.
  */
case class Position(x:Double,y:Double,direction:Double)

trait MovingObject {
  val position: Position
  val velocity: Double
  def move(): MovingObject
}

case class Player(position: Position, velocity: Double, turretDirection: Double, id: String) extends MovingObject {
  override def move(): Player =
    Player(
      Position(
        position.x + Helpers.xComp(velocity, position.direction),
        position.y + Helpers.yComp(velocity, position.direction),
        position.direction
      ),
      velocity,
      turretDirection,
      id
    )

  def changeSpeed(deltaVelocity: Double, deltaTurn: Double): Player =
    Player(
      Position(position.x, position.y, position.direction + deltaTurn),
      velocity + deltaVelocity,
      turretDirection,
      id
    )
}

case class Bullet(position: Position, velocity: Double) extends MovingObject {
  override def move(): Bullet = Bullet(
    Position(
      position.x + Helpers.xComp(velocity, position.direction),
      position.y + Helpers.yComp(velocity, position.direction),
      position.direction
    ),
    velocity
  )
}

sealed trait InEvent

sealed trait OutEvent

case class PlayerInput(move: Int, rotation: Int, turretRotation: Int, shot: Boolean) extends InEvent

case class RoomState(players: Seq[Player], bullets: Seq[Bullet]) extends OutEvent

private object Helpers {
  def xComp(v: Double, angle: Double): Double = v * cos(toRadians(angle))

  def yComp(v: Double, angle: Double): Double = v * sin(toRadians(angle))
}