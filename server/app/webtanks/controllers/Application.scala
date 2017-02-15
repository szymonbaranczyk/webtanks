package webtanks.controllers

import javax.inject.Inject
import play.api._
import play.api.mvc._
/**
  * Created by Szymon Barańczyk on 2017-02-13.
  */
class Application @Inject() extends Controller {

  def index = Action {
    Ok(webtanks.views.html.index("Your new application is ready."))
  }

}