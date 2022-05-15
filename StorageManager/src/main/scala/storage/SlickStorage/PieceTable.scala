package storage.SlickStorage

import slick.jdbc.PostgresProfile.api._

class PieceTable(tag: Tag) extends Table[(Int, String, String, Boolean, Boolean)](tag, "pieces") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

  def name: Rep[String] = column[String]("name")

  def piececolor: Rep[String] = column[String]("piececolor")

  def queen: Rep[Boolean] = column[Boolean]("queen")

  def kicked: Rep[Boolean] = column[Boolean]("kicked")

  override def * = (id, name, piececolor, queen, kicked)
}
