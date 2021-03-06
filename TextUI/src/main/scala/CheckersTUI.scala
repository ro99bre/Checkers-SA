package TextUI

import com.google.inject.Guice
import TextUI.aview.TextUI
import de.htwg.se.checkers.control.ControllerComponent.ControllerTrait
import de.htwg.se.checkers.model.GameComponent.GameBaseImpl.Color
import de.htwg.se.checkers.CheckersModule

object CheckersTUI {

  val injector = Guice.createInjector(new CheckersModule)
  val controller = injector.getInstance(classOf[ControllerTrait])

  def main(args: Array[String]): Unit = {

    println("Started Checkers in TUI Mode")

    val tui = new TextUI(controller)
    controller.notifyObservers()

    var input: String = ""
    if (args.length > 0) input = args(0)
    if(!input.isEmpty) {
      if (controller.getGame().getWinnerColor().isDefined)
        println("Winner: " + controller.getGame().getWinnerColor())
      else if (controller.getGame().getLastMoveColor() == Color.white)
        print("Next Player: Black\nNext move: ")
      else
        print("Next Player: White\nNext move: ")
      tui.tuiProcessor(input)
    } else {
      while (input != "exit")
      do (
        if (controller.getGame().getWinnerColor().isDefined)
          println("Winner: " + controller.getGame().getWinnerColor())
        else
          if (controller.getGame().getLastMoveColor() == Color.white) print("Next Player: Black\nNext move: ")
          else print("Next Player: White\nNext move: ")
            input = scala.io.StdIn.readLine()
            tui.tuiProcessor(input)
      )
    }
  }
}
