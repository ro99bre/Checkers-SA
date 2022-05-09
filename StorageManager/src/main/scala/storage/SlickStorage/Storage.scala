package storage.SlickStorage

import storage.StorageTrait
import slick.dbio.DBIO
import slick.jdbc.JdbcBackend
//import slick.jdbc.JdbcBackend.*
import slick.lifted.TableQuery

import slick.jdbc.PostgresProfile.api._

class Storage extends StorageTrait {

  val boardTable = TableQuery[Board]

  //object boardTable extends TableQuery(new Board(_)) {
  //  val findByName = this.findBy(_.name)
  //}

  val database = Database.forURL(
    url = "jdbc:postgresql://192.52.45.214:80/checkers",
    user = "checkers",
    password = "checkers@postgresql",
    driver = "org.postgresql.jdbc.Driver",
  )

  override def save(game: String): Unit = {
    val injection = DBIO.seq((boardTable.schema).createIfNotExists)
    database.run(injection)
    return
  }

  override def load(): String = {
    "Hello World"
  }
}
