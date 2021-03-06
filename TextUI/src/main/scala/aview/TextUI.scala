package TextUI.aview

import de.htwg.se.checkers.control.ControllerComponent.ControllerTrait
import de.htwg.se.checkers.control.ControllerComponent.controllerBaseImpl.Controller
import de.htwg.se.checkers.util.Observer

class TextUI(controller: ControllerTrait) extends Observer {

  controller.add(this)

  def tuiProcessor(input: String): Unit ={

    input.split(" |,").toList match {

      case "new" :: "Round" :: Nil => controller.createGame()

      case "move" :: xPosOld :: yPosOld :: xPosNew :: yPosNew :: Nil =>
        controller.move(xPosOld.toInt, yPosOld.toInt, xPosNew.toInt, yPosNew.toInt)

      case "undo" :: Nil => controller.undo()

      case "redo" :: Nil => controller.redo()

      case "save" :: Nil => controller.save()

      case "load" :: Nil => controller.load()

      case "exit" :: Nil =>

      case _ => println("\nPossible Commands:\nnew Round:\t\t\t\t\tStarts a new Round of the game. " +
        "The current scores will be lost.\nmove old<X,Y> new<X,Y>:\t\tMoves the Piece from the old position to the " +
        "new position specified\nexit:\t\t\t\t\t\tExit the Game.\n")

    }
  }
  override def update(): Unit = println(controller.gameToString)
}
