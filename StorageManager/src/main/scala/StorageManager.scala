package FileStorage

import FileStorage.FileStorageJson
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.*
import akka.http.scaladsl.server.Directives.*

import scala.io.StdIn

object StorageManager {

  def main(args: Array[String]): Unit = {

    val fileStorage = new FileStorageJson
    implicit val system = ActorSystem(Behaviors.empty, "my-system")
    implicit val executionContext = system.executionContext

    val route =
      concat(
        get {
          path("game") {
            complete(HttpEntity(ContentTypes.`application/json`, fileStorage.load()))
          }
        },
        post {
          path("game") {
            entity(as [String]) { game => fileStorage.save(game)
              complete("game saved")
            }
          }
        }
      )

    Http().newServerAt("0.0.0.0", 8001).bind(route)
  }
}
