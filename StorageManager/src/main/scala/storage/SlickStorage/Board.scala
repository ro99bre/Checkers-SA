package storage.SlickStorage

import slick.jdbc.PostgresProfile.api._

class Board(tag: Tag) extends Table[(Int, String)](tag, "BOARDS") {

  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

  def game = column[String]("Game")

  override def * = (id, game)
}
