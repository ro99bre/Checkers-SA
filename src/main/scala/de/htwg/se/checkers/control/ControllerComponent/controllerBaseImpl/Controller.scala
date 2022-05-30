package de.htwg.se.checkers.control.ControllerComponent.controllerBaseImpl

import akka.actor.typed.ActorSystem
import akka.actor.typed.javadsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpMethods, HttpRequest, HttpResponse}
import akka.http.scaladsl.unmarshalling.Unmarshal
import de.htwg.se.checkers.util.Command
import com.google.inject.{Guice, Inject}
import net.codingwell.scalaguice.InjectorExtensions.*
import de.htwg.se.checkers.CheckersModule
import de.htwg.se.checkers.control.ControllerComponent.ControllerTrait
import de.htwg.se.checkers.model.GameComponent.GameBaseImpl.{Color, Game}
import de.htwg.se.checkers.model.GameComponent.GameTrait
import de.htwg.se.checkers.util.UndoManager
import de.htwg.se.checkers.util.JsonHandler

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContextExecutor, Future}
import scala.util.{Failure, Success, Try}

class Controller @Inject() (var game:GameTrait) extends ControllerTrait {

  private val undoManager = new UndoManager
  val injector = Guice.createInjector(new CheckersModule)

  override def createGame():Unit = {
    game = injector.getInstance(classOf[GameTrait])
    notifyObservers()
  }

  override def move(sx:Int, sy:Int, dx:Int, dy:Int): Unit = {
    undoManager.doStep(new MoveCommand(sx,sy,dx,dy,this))
    notifyObservers()
  }

  override def undo() : Unit = {
    undoManager.undoStep()
    notifyObservers()
  }

  override def redo() : Unit = {
    undoManager.redoStep()
    notifyObservers()
  }

  override def save() : Unit = {
    val jsonHandler = new JsonHandler
    val gamestate: String = jsonHandler.generate(game)

    implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "SingleRequest")
    implicit val executionContext: ExecutionContextExecutor = system.executionContext
    val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(method = HttpMethods.POST, uri = "http://checkers-storage:8001/game", entity = gamestate))
  }

  override def load(): Future[String] = {
    val jsonHandler = new JsonHandler

    implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "SingleRequest")
    implicit val executionContext: ExecutionContextExecutor = system.executionContext

    val requestFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(uri = "http://checkers-storage:8001/game"))

    val response = Await.result(requestFuture, Duration.Inf)

    if(!requestFuture.isInstanceOf[Future[Success[HttpResponse]]]) {
      println("Error Loading Data from Database")
    }

    val responseText = Await.result(Unmarshal(response.entity).to[String], Duration.Inf)

    game = jsonHandler.decode(responseText.toString)
    notifyObservers()

    return Future.successful(responseText.toString)
  }

  override def getGame(): GameTrait = game

  override def gameToString:String = game.toString
}
