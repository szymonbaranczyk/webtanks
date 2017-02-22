package webtanks.controllers

import javax.inject.Inject

import play.api.mvc._
import shared.{Player, Position, RoomState}
import upickle.default._
/**
  * Created by Szymon Barańczyk on 2017-02-13.
  */
class Application @Inject() extends Controller {

  def index = Action {
    val roomState = RoomState(Seq(Player(Position(0, 0, 0), 0, 0, "id")), Seq())
    Ok(webtanks.views.html.index(write(roomState)))
  }

}