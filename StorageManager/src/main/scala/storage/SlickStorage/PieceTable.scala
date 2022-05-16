package storage.SlickStorage

import slick.jdbc.PostgresProfile.api._

class PieceTable(tag: Tag) extends Table[(Int, String, Int, String, String, String)](tag, "pieces") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

  def name: Rep[String] = column[String]("name")

  def index: Rep[Int] = column[Int]("index")

  def piececolor: Rep[String] = column[String]("piececolor")

  def queen: Rep[String] = column[String]("queen")

  def kicked: Rep[String] = column[String]("kicked")

  override def * = (id, name, index, piececolor, queen, kicked)
}
