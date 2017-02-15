import scala.scalajs.js
import shared._
import upickle._
import upickle.default._
/**
  * Created by SBARANCZ on 2017-02-13.
  */
object ScalaJSApp extends js.JSApp {
  override def main(): Unit = {
    val json = write(GameState(Seq()))
    println(read[OutEvent](json))
  }

}
