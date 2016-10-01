package controllers

import play.api.mvc._
import play.api.libs.ws._
import play.api.Play.current
import javax.inject.Inject

class Application extends Controller {

  def index = Action {
    Ok(views.html.index("Mandala Master API Server"))
  }
}