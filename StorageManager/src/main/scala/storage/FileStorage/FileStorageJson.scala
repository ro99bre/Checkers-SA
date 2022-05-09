package FileStorage

import scala.io.{BufferedSource, Source}
import scala.util.{Failure, Success, Try}

class FileStorageJson {
  def save(game: String): Unit = {
    fileSaver(game) match {
      case Success(option) => option
      case Failure(exception) => print("Error saving to file")
    }
  }

  def fileSaver(game: String): Try[Unit] = {
    import java.io._
    println("Save in FileStorage")
    Try {
      val pw = new PrintWriter(new File("game.json"))
      pw.write(game)
      pw.close()
    }
  }

  def load(): String = {
    fileLoader("game.json") match {
      case Success(value) => value
      case Failure(exception) => "Error loading file"
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
