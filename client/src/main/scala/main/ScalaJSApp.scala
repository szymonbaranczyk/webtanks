package main


import org.scalajs.dom._
import org.scalajs.dom.html.Input
import org.scalajs.jquery.{jQuery => $}

import scala.scalajs.js
import scala.scalajs.js.annotation.JSExport

/**
  * Created by Szymon Barańczyk on 2017-02-13.
  */


@JSExport
object ScalaJSApp extends js.JSApp {


  override def main(): Unit = {
    $(document).ready(() => new WebSocketService(new TextAreaDebugger))
  }
}

class TextAreaDebugger extends Debugger {
  val debugWindow: Input = document.getElementById("debug-window").asInstanceOf[html.Input]

  override def debug(str: String): Unit = {
    debugWindow.value = str + "\n" + debugWindow.value
  }
}


