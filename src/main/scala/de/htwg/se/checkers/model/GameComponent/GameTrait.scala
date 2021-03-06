package de.htwg.se.checkers.model.GameComponent

import de.htwg.se.checkers.model.GameComponent.GameBaseImpl.{Board, Cell, Color, Kicked, Piece, Queen}

trait GameTrait {

  def movePiece(s:CellTrait, d:CellTrait): GameTrait
  def undoMove(s:CellTrait, d:CellTrait): GameTrait
  def updatePiece(s:CellTrait, d:CellTrait): Board
  def checkRules(s:CellTrait, d:CellTrait): (Option[Vector[Piece]], Option[Vector[Piece]], Color.Value)
  def pieceColorCheck(start:CellTrait): Boolean
  def cellColorCheck(destination:CellTrait): Boolean
  def cellEmptyCheck(destination:CellTrait): Boolean
  def opponentPieces(start:CellTrait): Vector[Piece]
  def moveQueenRules(s:CellTrait, d:CellTrait): Option[Vector[Piece]]
  def queenDestinationCheck(start:CellTrait, destination:CellTrait): Vector[Piece]
  def deQueenDestinationCheck(s:CellTrait, d:CellTrait, tempboard:Board) : Option[(Board,Vector[Piece],Vector[Piece])]
  def crown(pieces: Vector[Piece], index: Int): Vector[Piece]
  def deCrown(pieces: Vector[Piece], index: Int): Vector[Piece]
  def moveRules(s:CellTrait, d:CellTrait, c:Color.Value): Option[Vector[Piece]]
  def kickPieceCheck(s:CellTrait, d:CellTrait): Option[Vector[Piece]]
  def updatePiecesKicked(middlePiece: Piece): Vector[Piece]
  def deKickPieceCheck(s:CellTrait, d:CellTrait, tempboard:Board, opponentColor:Color.Value) : Option[(Board,Vector[Piece],Vector[Piece])]
  def kickPiece(pieces: Vector[Piece], index: Int): Vector[Piece]
  def deKickPiece(pieces: Vector[Piece], index: Int): Vector[Piece]
  def middleCellCalc(s:CellTrait, d:CellTrait): Option[CellTrait]
  def middleCellCheck(start:CellTrait, middleCell:CellTrait): Boolean
  def countKickedPieces(): (Int, Int)
  def countKickedPieces(pieces: Vector[Piece]): Int
  def cell(y:Int, x:Int): CellTrait
  def hasWon(start:CellTrait, destination:CellTrait) : Option[Color.Value]
  def isBlocked(board: Board) : Option[Color.Value]
  def isColorBlocked(board: Board, color: Color.Value): Boolean
  def plusCheck(start:CellTrait, board: Board): Boolean
  def minusCheck(start:CellTrait, board: Board) : Boolean
  def updateGame(cell: CellTrait, lmc:Color.Value, piece: Option[Piece] = None, index: Option[Int] = None, winner:Option[Color.Value] = None) : GameTrait

  def getLastMoveColor() : Color.Value
  def getWinnerColor() : Option[Color.Value]
  def getBoard(): Board
  def getPB(): Vector[Piece]
  def getPW(): Vector[Piece]

  def setPiece(index:Int, pieces:Vector[Piece], color:Color.Value, queen:Queen.Value, kicked:Kicked.Value) : Vector[Piece]
}

trait CellTrait {

  def x:Int
  def y:Int
  def color:Color.Value
  def piece:Option[Piece]

}
