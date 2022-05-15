package storage.SlickStorage

import slick.jdbc.PostgresProfile.api._

class GameTable(tag: Tag) extends Table[(Int, String, String, Option[String])](tag, "games") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

  def name: Rep[String] = column[String]("name")

  def lmc: Rep[String] = column[String]("lmc")

  def winnerColor: Rep[Option[String]] = column[Option[String]]("winnerColor")

  override def * = (id, name, lmc, winnerColor)
}
