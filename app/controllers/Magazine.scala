package controllers

import javax.inject.Inject

import anorm._
import play.api.Play.current
import play.api.db.DBApi
import play.api.libs.json.{JsString, JsNumber, JsNull, Json}
import play.api.mvc._
import play.api.Logger
import java.util.Date

class Magazine @Inject()(dbapi: DBApi) extends Controller {

  private val db = dbapi.database("default")

  def list = Action {

    db.withConnection { implicit conn =>
      val records = SQL(
        """select title, title_path, kind, max(concat(lpad(year, 4, '0'), '-', lpad(month, 2, '0'))) as latest_data
          | from magazine m inner join retension_info ri on m.id = ri.magazine_id
          | group by m.title_path
        """.stripMargin
      )().map { row => 
        Json.obj(
          "title" -> row[String]("title"),
          "title_path" -> row[String]("title_path"),
          "kind" -> (row[Byte]("kind") match {
              case 0 => "不明"
              case 1 => "冊子"
              case 2 => "WEB"
              case _ => "不明"}),
          "latest_data" -> row[String]("latest_data")
        )
      }

      Ok(Json.toJson(records))
    }
  }
  
  def latestMangaData(name: String) = Action {
  
    db.withConnection { implicit conn =>
      val magazine_records = SQL(
        """select m.id as magazine_id, max(concat(lpad(year, 4, '0'), '-', lpad(month, 2, '0'))) as latest_data
          | from magazine m inner join retension_info ri on m.id = ri.magazine_id
          | where m.title_path = {name}
          | group by m.title_path""".stripMargin
      ).on("name" -> name)().map { row =>
        row[Int]("magazine_id") -> row[String]("latest_data")
      }.toList
      
      Logger.debug(magazine_records.toString())
      if (magazine_records.size == 0) {
        Logger.warn(s"Execute no data request name=$name")
        Ok("[]")
      }
      
      val result = magazine_records.map { magazine =>
        val records = SQL(
          """select id, title, title_short, author, original_author, support_author, public_url,
            | twitter_account, twitter_hash_tag, target_sex, release_status, created_at, updated_at
            | from manga
            | where magazine_id = {magazine_id}
            | and start_month <= {latest_data}
            | and (end_month is null or end_month >= {latest_data})""".stripMargin
          ).on(
            "magazine_id" -> magazine._1,
            "latest_data" -> magazine._2
          )().map { row =>
            Map(
              "id" -> JsNumber(row[Int]("id")),
              "title" -> JsString(row[String]("title")),
              "title_short" -> JsString(row[Option[String]]("title_short").orNull),
              "author" -> JsString(row[String]("author")),
              "original_author" -> JsString(row[Option[String]]("original_author").orNull),
              "support_author" -> JsString(row[Option[String]]("support_author").orNull),
              "public_url" -> JsString(row[Option[String]]("public_url").orNull),
              "twitter_account" -> JsString(row[Option[String]]("twitter_account").orNull),
              "twitter_hash_tag" -> JsString(row[Option[String]]("twitter_hash_tag").orNull),
              "target_sex" -> JsString(row[Int]("target_sex") match {
                case 0 => "不明"
                case 1 => "男"
                case 2 => "女"
                case 3 => "両方"
                case _ => "不明"}),
              "release_status" -> JsString(row[Int]("release_status") match {
                case 0 => "不明"
                case 1 => "新連載"
                case 2 => "連載中"
                case 3 => "連載終了"
                case 4 => "読み切り"
                case 5 => "休載中"
                case _ => "不明"}),
              "created_at" -> JsString(row[Date]("created_at").toString),
              "updated_at" -> JsString(row[Date]("updated_at").toString)
            )
        }.toList
        
        records
      }
      Ok(Json.toJson(result))
    }
  }
}
