package storage.SlickStorage

import slick.dbio.DBIO
import storage.StorageTrait
import slick.jdbc.JdbcBackend

import scala.concurrent.duration.Duration
import scala.collection.mutable.ListBuffer
import scala.concurrent.Await
import slick.lifted.TableQuery

import slick.jdbc.PostgresProfile.api._

class Storage extends StorageTrait {

  val boardTable = new TableQuery(new Board(_))

  val database = Database.forURL(
    url = "jdbc:postgresql://checkers-db/checkers",
    user = "checkers",
    password = "checkers@postgresql",
    driver = "org.postgresql.jdbc.Driver",
  )

  override def save(game: String): Unit = {
    val injection = DBIO.seq((boardTable.schema).createIfNotExists, boardTable += (0, game))
    database.run(injection)
  }

  override def load(): String = {
    val result = Await.result(database.run(boardTable.filter(_.id.equals(0)).result), Duration.Inf)
    result.head._2
  }
}
