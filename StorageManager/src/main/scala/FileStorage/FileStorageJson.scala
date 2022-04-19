package FileStorage

import scala.io.{BufferedSource, Source}
import scala.util.{Success, Try}

class FileStorageJson {
  def save(game: String): Try[Unit] = {
    import java.io._
    println("Save in FileStorage")
    Try {
      val pw = new PrintWriter(new File("game.json"))
      pw.write(game)
      pw.close()
    }
  }
  
  def load(): Try[String] = {
    Try {
      val source: BufferedSource = Source.fromFile("game.json")
      val sourceString: String = source.getLines().mkString
      source.close()
      sourceString
    }
  }
}
