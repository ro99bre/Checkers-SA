package storage.SlickStorage

import slick.jdbc.PostgresProfile.api._

class CellTable(tag: Tag) extends Table[(Int, String, Int, Int, String, Option[String], Option[String], Option[String])](tag, "cells") {
  def id: Rep[Int] = column[Int]("id", O.PrimaryKey, O.AutoInc)

  def name: Rep[String] = column[String]("name")

  def x: Rep[Int] = column[Int]("x")

  def y: Rep[Int] = column[Int]("y")
  
  def color: Rep[String] = column[String]("color")

  def piececolor: Rep[Option[String]] = column[Option[String]]("piececolor")

  def queen: Rep[Option[String]] = column[Option[String]]("queen")

  def kicked: Rep[Option[String]] = column[Option[String]]("kicked")

  override def * = (id, name, x, y, color, piececolor, queen, kicked)
}
