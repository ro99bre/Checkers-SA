package de.htwg.se.checkers.model

case class Matrix[T](rows:Vector[Vector[T]]) {
  def this(filling:T) = this(Vector.tabulate(8, 8){(i,j) => filling})

  def cell(y:Int, x:Int): T = rows(y)(x)

  def replaceCell(y:Int, x:Int, cell:T): Matrix[T] = copy(rows.updated(y, rows(y).updated(x, cell)))
}
