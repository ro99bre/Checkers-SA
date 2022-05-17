package storage

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.*
import akka.http.scaladsl.server.Directives.*
import model.GameComponent.GameTrait
import util.JsonHandler.JsonHandler
import com.google.inject.Guice

import scala.io.StdIn

object StorageManager {

  val injector = Guice.createInjector(new StorageModule)
  val databaseStorage = injector.getInstance(classOf[StorageTrait])

  def main(args: Array[String]): Unit = {

    val jsonHandler = new JsonHandler
    implicit val system = ActorSystem(Behaviors.empty, "my-system")
    implicit val executionContext = system.executionContext

    val route =
      concat(
        get {
          path("game") {
            complete(HttpEntity(ContentTypes.`application/json`, jsonHandler.generate(databaseStorage.load())))
          }
        },
        post {
          path("game") {
            entity(as [String]) { json => databaseStorage.save(jsonHandler.decode(json))
              complete("game saved")
            }
          }
        }
      )

    Http().newServerAt("0.0.0.0", 8001).bind(route)
  }
}
