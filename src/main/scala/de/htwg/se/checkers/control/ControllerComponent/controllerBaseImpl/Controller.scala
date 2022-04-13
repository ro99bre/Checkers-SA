package de.htwg.se.checkers.control.ControllerComponent.controllerBaseImpl
import de.htwg.se.checkers.util.Command
import com.google.inject.{Guice, Inject}
import net.codingwell.scalaguice.InjectorExtensions._
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
    fileIo.save(game) match {
      case Success(option) =>
        notifyObservers()
        println("Game saved")
      case Failure(exception) =>
        println("Saving failed")
    }
  }

  override def load(): Unit = {
    val loadedGame: Try[GameTrait] = fileIo.load()
    loadedGame match {
      case Success(option) =>
        game = option
        println("Loaded Game")
        notifyObservers()
      case Failure(exception) =>
        println("Failed to load Game")
        game
    }
  }

  override def getGame(): GameTrait = game

  override def gameToString:String = game.toString
}