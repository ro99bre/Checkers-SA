package de.htwg.se.checkers

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives.{as, complete, concat, entity, get, path, post}
import akka.http.scaladsl.server.Directives._
import com.google.inject.Guice
import de.htwg.se.checkers.control.ControllerComponent.ControllerTrait

import play.api.libs.json.{JsObject, JsValue, Json, Writes}

object CheckersBase {

  val injector = Guice.createInjector(new CheckersModule)
  val controller = injector.getInstance(classOf[ControllerTrait])

  def main(args: Array[String]): Unit = {

    implicit val system = ActorSystem(Behaviors.empty, "my-system")
    implicit val executionContext = system.executionContext

    val route =
      concat(
        post {
          path("game" / "create") {
            controller.createGame()
            complete(HttpEntity(ContentTypes.`application/json`, controller.gameToString))
           }
        },
        put {
          path("game" / "move") {
            entity(as [String]) { game =>
              val json: JsValue = Json.parse(game)

              val source = (json \\ "from").head
              val sX: Int = (source \\ "x") (0).as[Int]
              val sY: Int = (source \\ "y") (0).as[Int]

              val destination = (json \\ "to").head
              val destX: Int = (destination \\ "x") (0).as[Int]
              val destY: Int = (destination \\ "y") (0).as[Int]

              controller.move(sX, sY, destX, destY)
              complete(HttpEntity(ContentTypes.`application/json`, controller.gameToString))
            }
          }
        },
        delete {
          path("game" / "undo") {
            controller.undo()
            complete(HttpEntity(ContentTypes.`application/json`, controller.gameToString))
          }
        },
        put {
          path("game" / "redo") {
            controller.redo()
            complete(HttpEntity(ContentTypes.`application/json`, controller.gameToString))
          }
        },
        post {
          path("game" / "save") {
            controller.save()
            complete(HttpEntity(ContentTypes.`application/json`, controller.gameToString))
          }
        },
        get {
          path("game" / "load") {
            controller.load()
            complete(HttpEntity(ContentTypes.`application/json`, controller.gameToString))
          }
        },
      )

    Http().newServerAt("0.0.0.0", 8080).bind(route)
  }
}
