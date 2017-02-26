package main

import org.scalajs.dom._
import org.scalajs.dom.raw.WebSocket
import org.scalajs.dom.window.{clearInterval, setInterval}
import org.scalajs.jquery.{jQuery => $}
import shared._
import upickle.default._

import scala.scalajs.js.annotation.JSExport

/**
  * Created by Szymon Barańczyk on 2017-02-24.
  */

@JSExport
class WebSocketService(d: Debugger) {
  def openConnection(login: String): Unit = {
    val socket = new WebSocket(getWebsocketUri(login))
    socket.onopen = (e: Event) => {
      d.debug("WebSocket connection is successful!")
    }
    socket.onmessage = (m: MessageEvent) => {
      read[OutEvent](m.data.toString) match {
        case p: PlayerState => d.debug(p.toString)
        case b: BulletState => d.debug(b.toString)
      }
    }
    val interval = setInterval(() => {
      d.debug("message sent")
      socket.send(write(PlayerInput(1, 0, 1)))
    }, 1000)
    socket.onclose = (_: Event) => {
      d.debug("WebSocket closed")
      clearInterval(interval)
    }
  }

  private def getWebsocketUri(login: String): String = {
    val wsProtocol = if (document.location.protocol == "https:") "wss" else "ws"

    s"$wsProtocol://${document.location.host}/websocket?login=$login"

  }

  private val button = document.getElementById("connect").asInstanceOf[html.Button]
  button.onclick = (_: MouseEvent) => {
    openConnection("test")
  }

}


trait Debugger {
  def debug(string: String)
}


