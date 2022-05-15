package storage.SlickStorage

import slick.jdbc.PostgresProfile.api._

class CellTable(tag: Tag) extends Table[(Int, String, Int, Int, String, Option[String], Option[Boolean], Option[Boolean])](tag, "cells") {
  def id: Rep[Int] = column[Int]("id", O.PrimaryKey, O.AutoInc)

  def name: Rep[String] = column[String]("name")

  def x: Rep[Int] = column[Int]("x")

  def y: Rep[Int] = column[Int]("y")
  
  def color: Rep[String] = column[String]("color")

  def piececolor: Rep[Option[String]] = column[Option[String]]("piececolor")

  def queen: Rep[Option[Boolean]] = column[Option[Boolean]]("queen")

  def kicked: Rep[Option[Boolean]] = column[Option[Boolean]]("kicked")

  override def * = (id, name, x, y, color, piececolor, queen, kicked)
}
