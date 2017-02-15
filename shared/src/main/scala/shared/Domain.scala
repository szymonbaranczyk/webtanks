package shared

/**
  * Created by SBARANCZ on 2017-02-13.
  */
case class Position(x:Double,y:Double,direction:Double)

trait MovingObject {
  val position: Position
  val speed: Double
  def move(): MovingObject
}

case class Player(position: Position, speed: Double, turretDirection: Double) extends MovingObject {
  override def move(): MovingObject = this  //TODO: Calculations

  def changeSpeed(speed: Double, turn: Double): Player = Player(
    Position(position.x,position.y,position.direction+turn),
    speed,
    turretDirection
  )
}

case class Bullet(position: Position, speed: Double) extends MovingObject {
  override def move(): MovingObject = this  //TODO: Calculations
}

sealed trait InEvent

sealed trait OutEvent

case class PlayerInput(move: Int, rotation: Int, turretRotation: Int, shot: Boolean) extends InEvent

case class GameState(players: Seq[Player]) extends OutEvent