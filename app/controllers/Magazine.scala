package controllers

import javax.inject.Inject

import anorm._
import play.api.Play.current
import play.api.db.DBApi
import play.api.libs.json.{JsString, JsNumber, Json}
import play.api.mvc._

class Magazine @Inject()(dbapi: DBApi) extends Controller {

  private val db = dbapi.database("default")

  def list = Action {

    db.withConnection { implicit c =>
      val records = SQL(
        """select title, title_path, kind, max(concat(lpad(year, 4, '0'), '-', lpad(month, 2, '0'))) as lastest_data
          | from magazine m inner join retension_info ri on m.id = ri.magazine_id
          | group by m.title_path
        """.stripMargin
      )().map { row => 
        Json.obj(
          "title" -> row[String]("title"),
          "title_path" -> row[String]("title_path"),
          "kind" -> (row[Int]("kind") match {
              case 0 => "不明"
              case 1 => "冊子"
              case 2 => "WEB"
              case _ => "不明"}),
          "lastest_data" -> row[String]("lastest_data")
        )
      }

      Ok(Json.toJson(records))
    }
  }
}
