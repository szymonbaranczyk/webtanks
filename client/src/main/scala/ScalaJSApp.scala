import shared._
import upickle.default._

import scala.scalajs.js
/**
  * Created by Szymon Barańczyk on 2017-02-13.
  */
object ScalaJSApp extends js.JSApp {
  override def main(): Unit = {
    val json = write(PlayerInput(1, 1, 1, true))
    println(read[InEvent](json))
  }

  def openConnection(): Unit = {
  }
}
