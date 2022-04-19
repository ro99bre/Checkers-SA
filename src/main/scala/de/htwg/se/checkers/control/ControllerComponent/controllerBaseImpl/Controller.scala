package de.htwg.se.checkers.control.ControllerComponent.controllerBaseImpl
import FileStorage.FileStorageJson
import de.htwg.se.checkers.util.Command
import com.google.inject.{Guice, Inject}
import net.codingwell.scalaguice.InjectorExtensions.*
import de.htwg.se.checkers.CheckersModule
import de.htwg.se.checkers.control.ControllerComponent.ControllerTrait
import de.htwg.se.checkers.model.FileIOComponent.FileIOTrait
import de.htwg.se.checkers.model.GameComponent.GameBaseImpl.{Color, Game}
import de.htwg.se.checkers.model.GameComponent.GameTrait
import de.htwg.se.checkers.util.UndoManager

import scala.util.{Failure, Success, Try}

class Controller @Inject() (var game:GameTrait) extends ControllerTrait {

  private val undoManager = new UndoManager
  val injector = Guice.createInjector(new CheckersModule)
  val fileIo = injector.getInstance(classOf[FileIOTrait])

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
    val storage = new FileStorageJson
    storage.save(jsonHandler.generate(game)) match {
      case Success(option) =>
        println("Game saved")
        notifyObservers()
      case Failure(exception) =>
        println("Saving failed")
    }
  }

  override def load(): Unit = {
    val jsonHandler = new JsonHandler
    val storage = new FileStorageJson
    storage.load() match {
      case Success(option) =>
        game = jsonHandler.decode(option)
        println("Game loaded")
        notifyObservers()
      case Failure(exception) =>
        println("Loading failed")
    }
  }

  override def getGame(): GameTrait = game

  override def gameToString:String = game.toString
}