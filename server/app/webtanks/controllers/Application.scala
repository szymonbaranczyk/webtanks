package webtanks.controllers

import javax.inject.Inject

import play.api.mvc._
import shared.{PlayerState, Position}
import upickle.default._
/**
  * Created by Szymon Barańczyk on 2017-02-13.
  */
class Application @Inject() extends Controller {

  def index = Action {
    val roomState = PlayerState(Position(0, 0, 0), 0, 0, "id")
    Ok(webtanks.views.html.index(write(roomState)))
  }

}