package controllers

import java.util.Date

import anorm._
import play.api.Play.current
import play.api.db._
import play.api.libs.json.{JsString, JsNumber, Json}
import play.api.mvc._
import play.api.Logger

object Magazine extends Controller {

  def list = Action {
    val records = "{'test'}"
    Ok(Json.toJson(records))
  }
}
