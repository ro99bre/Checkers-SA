import de.htwg.se.checkers.model.GameComponent.CellTrait
import de.htwg.se.checkers.model.GameComponent.GameBaseImpl.{Cell, Color, Queen}


def test(cell: Cell): Boolean = {
  var bool: Boolean = false

  (cell.piece.isDefined, cell.color, cell.piece.get.queen) match {
    case (true, Color.black, Queen.isQueen) =>
      if (cell.x == 1) bool = true
      else return false
    case (true, Color.white, Queen.isQueen) =>
      if (cell.x == 1) bool = true
      else return false
  }
  return false
  //return "x:" + x + " y:" + y
}


(0 until 8) map( i =>
  (0 until 8) map ( j => test(Cell(i,j,Color.black)) )
)