package model.GameComponent.GameBaseImpl

import model.GameComponent.CellTrait

case class Cell(y:Int, x:Int, color:Color.Value, piece:Option[Piece] = None) extends CellTrait

