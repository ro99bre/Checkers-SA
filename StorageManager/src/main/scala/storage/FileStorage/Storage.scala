package storage.FileStorage

import com.google.inject.Inject
import model.GameComponent.GameBaseImpl.Game
import model.GameComponent.GameTrait
import storage.StorageTrait
import util.JsonHandler.JsonHandler

import scala.io.{BufferedSource, Source}
import scala.util.{Failure, Success, Try}

class Storage @Inject()extends StorageTrait {

  val jsonHandler = new JsonHandler

  override def save(game: GameTrait): Unit = {
    fileSaver(jsonHandler.generate(game)) match {
      case Success(option) => option
      case Failure(exception) => print("Error saving to file")
    }
  }

  def fileSaver(game: String): Try[Unit] = {
    import java.io._
    println("Save in storage.FileStorage")
    Try {
      val pw = new PrintWriter(new File("game.json"))
      pw.write(game)
      pw.close()
    }
  }

  override def load(): GameTrait = {
    fileLoader("game.json") match {
      case Success(value) => jsonHandler.decode(value)
      case Failure(exception) => {println("Error loading file"); new Game}
    }
  }

  def fileLoader(filename: String): Try[String] = {
    Try{
      val file = Source.fromFile(filename)
      val game: String = file.getLines().mkString
      file.close()
      game
    }
  }
}
