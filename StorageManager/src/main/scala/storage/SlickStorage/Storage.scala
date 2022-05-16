package storage.SlickStorage

import com.google.inject.Guice
import model.GameComponent.GameBaseImpl.{Board, Color, Game, Kicked, Piece, Queen}

import storage.StorageTrait
import storage.StorageModule
import model.GameComponent.GameTrait
import slick.dbio.DBIO
import slick.lifted.TableQuery
import slick.jdbc.JdbcBackend
import slick.jdbc.JdbcBackend.Database
import slick.jdbc.PostgresProfile.api.*

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

class Storage extends StorageTrait {

  val database = Database.forURL(
    url = "jdbc:postgresql://checkers-db/checkers",
    user = "checkers",
    password = "checkers@postgresql",
    driver = "org.postgresql.Driver",
  )

  val cellTable = new TableQuery(new CellTable(_))
  val pieceTable = new TableQuery(new PieceTable(_))
  val gameTable = new TableQuery(new GameTable(_))

  override def save(game: GameTrait): Unit = {
    database.run(cellTable.schema.createIfNotExists)
    database.run(pieceTable.schema.createIfNotExists)
    database.run(gameTable.schema.createIfNotExists)

    for {
      row <- 0 until 8
      col <- 0 until 8
    } yield {

      if(game.cell(col, row).piece.isEmpty) {
        database.run(DBIO.seq(cellTable += (0, "game1", col, row, game.cell(col, row).color.toString,
          None, None, None)))
      } else {
        database.run(DBIO.seq(cellTable += (0, "game1", col, row, game.cell(col, row).color.toString,
          Some(game.cell(col, row).piece.get.color.toString),
          Some(game.cell(col, row).piece.get.queen.toString),
          Some(game.cell(col, row).piece.get.kicked.toString))
        ))
      }
    }

    for {index <- 0 until 12} yield {
      database.run(DBIO.seq(
        pieceTable += (0, "game1", index,
          game.getPW()(index).color.toString,
          game.getPW()(index).queen.toString,
          game.getPW()(index).kicked.toString),
        pieceTable += (0, "game1", index,
          game.getPB()(index).color.toString,
          game.getPB()(index).queen.toString,
          game.getPB()(index).kicked.toString))
      )
    }

    if(game.getWinnerColor().isDefined) {
      database.run(DBIO.seq(gameTable += (
        0, "game1", game.getLastMoveColor().toString, Some(game.getLastMoveColor().toString))))
    } else {
      database.run(DBIO.seq(gameTable += (
        0, "game1", game.getLastMoveColor().toString, None)))

    }
  }

  override def load(): GameTrait = {
    val injector = Guice.createInjector(new StorageModule)
    var game: GameTrait = injector.getInstance(classOf[GameTrait])
    var board: Board = game.getBoard()
    var pb: Vector[Piece] = game.getPB()
    var pw: Vector[Piece] = game.getPW()
    var lmc: Color.Value = Color.white
    var winnerColor: Option[Color.Value] = None

    //Cells
    Await.result(database.run(cellTable.filter(_.name.equals("game1")).filter(_.color.equals("black")).filter(_.piececolor.equals(null)).result
        .map(_.foreach(f => board.setCell(f._3, f._4, Color.black, None, None, None)))), Duration.Inf)

    Await.result(database.run(cellTable.filter(_.name.equals("game1")).filter(_.color.equals("white")).filter(_.piececolor.equals(null)).result
      .map(_.foreach(f => board.setCell(f._3, f._4, Color.white, None, None, None)))), Duration.Inf)

    Await.result(database.run(cellTable.filter(_.name.equals("game1")).filter(_.color.equals("black"))
      .filter(_.piececolor.equals("black")).filter(_.queen.equals("notQueen")).filter(_.kicked.equals("notKicked")).result
      .map(_.foreach(f => board.setCell(f._3, f._4, Color.black, Some(Color.black), Some(Queen.notQueen), Some(Kicked.notKicked))))), Duration.Inf)

    Await.result(database.run(cellTable.filter(_.name.equals("game1")).filter(_.color.equals("black"))
      .filter(_.piececolor.equals("white")).filter(_.queen.equals("notQueen")).filter(_.kicked.equals("notKicked")).result
      .map(_.foreach(f => board.setCell(f._3, f._4, Color.black, Some(Color.white), Some(Queen.notQueen), Some(Kicked.notKicked))))), Duration.Inf)

    Await.result(database.run(cellTable.filter(_.name.equals("game1")).filter(_.color.equals("black"))
      .filter(_.piececolor.equals("black")).filter(_.queen.equals("isQueen")).filter(_.kicked.equals("notKicked")).result
      .map(_.foreach(f => board.setCell(f._3, f._4, Color.black, Some(Color.black), Some(Queen.isQueen), Some(Kicked.notKicked))))), Duration.Inf)

    Await.result(database.run(cellTable.filter(_.name.equals("game1")).filter(_.color.equals("black"))
      .filter(_.piececolor.equals("white")).filter(_.queen.equals("isQueen")).filter(_.kicked.equals("notKicked")).result
      .map(_.foreach(f => board.setCell(f._3, f._4, Color.black, Some(Color.white), Some(Queen.isQueen), Some(Kicked.notKicked))))), Duration.Inf)

    Await.result(database.run(cellTable.filter(_.name.equals("game1")).filter(_.color.equals("black"))
      .filter(_.piececolor.equals("black")).filter(_.queen.equals("notQueen")).filter(_.kicked.equals("isKicked")).result
      .map(_.foreach(f => board.setCell(f._3, f._4, Color.black, Some(Color.black), Some(Queen.notQueen), Some(Kicked.isKicked))))), Duration.Inf)

    Await.result(database.run(cellTable.filter(_.name.equals("game1")).filter(_.color.equals("black"))
      .filter(_.piececolor.equals("white")).filter(_.queen.equals("notQueen")).filter(_.kicked.equals("isKicked")).result
      .map(_.foreach(f => board.setCell(f._3, f._4, Color.black, Some(Color.white), Some(Queen.notQueen), Some(Kicked.isKicked))))), Duration.Inf)

    Await.result(database.run(cellTable.filter(_.name.equals("game1")).filter(_.color.equals("black"))
      .filter(_.piececolor.equals("black")).filter(_.queen.equals("isQueen")).filter(_.kicked.equals("isKicked")).result
      .map(_.foreach(f => board.setCell(f._3, f._4, Color.black, Some(Color.black), Some(Queen.isQueen), Some(Kicked.isKicked))))), Duration.Inf)

    Await.result(database.run(cellTable.filter(_.name.equals("game1")).filter(_.color.equals("black"))
      .filter(_.piececolor.equals("white")).filter(_.queen.equals("isQueen")).filter(_.kicked.equals("isKicked")).result
      .map(_.foreach(f => board.setCell(f._3, f._4, Color.black, Some(Color.white), Some(Queen.isQueen), Some(Kicked.isKicked))))), Duration.Inf)

    //Pieces
    Await.result(database.run(pieceTable.filter(_.name.equals("game1")).filter(_.piececolor.equals("black"))
      .filter(_.queen.equals("notQueen")).filter(_.kicked.equals("notKicked")).result
      .map(_.foreach(f => pb = game.setPiece(f._3, pb, Color.black, Queen.notQueen, Kicked.notKicked)))), Duration.Inf)

    Await.result(database.run(pieceTable.filter(_.name.equals("game1")).filter(_.piececolor.equals("white"))
      .filter(_.queen.equals("notQueen")).filter(_.kicked.equals("notKicked")).result
      .map(_.foreach(f => pw = game.setPiece(f._3, pb, Color.white, Queen.notQueen, Kicked.notKicked)))), Duration.Inf)

    Await.result(database.run(pieceTable.filter(_.name.equals("game1")).filter(_.piececolor.equals("black"))
      .filter(_.queen.equals("isQueen")).filter(_.kicked.equals("notKicked")).result
      .map(_.foreach(f => pb = game.setPiece(f._3, pb, Color.black, Queen.isQueen, Kicked.notKicked)))), Duration.Inf)

    Await.result(database.run(pieceTable.filter(_.name.equals("game1")).filter(_.piececolor.equals("white"))
      .filter(_.queen.equals("isQueen")).filter(_.kicked.equals("notKicked")).result
      .map(_.foreach(f => pw = game.setPiece(f._3, pb, Color.white, Queen.isQueen, Kicked.notKicked)))), Duration.Inf)

    Await.result(database.run(pieceTable.filter(_.name.equals("game1")).filter(_.piececolor.equals("black"))
      .filter(_.queen.equals("notQueen")).filter(_.kicked.equals("isKicked")).result
      .map(_.foreach(f => pb = game.setPiece(f._3, pb, Color.black, Queen.notQueen, Kicked.isKicked)))), Duration.Inf)

    Await.result(database.run(pieceTable.filter(_.name.equals("game1")).filter(_.piececolor.equals("white"))
      .filter(_.queen.equals("notQueen")).filter(_.kicked.equals("isKicked")).result
      .map(_.foreach(f => pw = game.setPiece(f._3, pb, Color.white, Queen.notQueen, Kicked.isKicked)))), Duration.Inf)

    Await.result(database.run(pieceTable.filter(_.name.equals("game1")).filter(_.piececolor.equals("black"))
      .filter(_.queen.equals("isQueen")).filter(_.kicked.equals("isKicked")).result
      .map(_.foreach(f => pb = game.setPiece(f._3, pb, Color.black, Queen.isQueen, Kicked.isKicked)))), Duration.Inf)

    Await.result(database.run(pieceTable.filter(_.name.equals("game1")).filter(_.piececolor.equals("white"))
      .filter(_.queen.equals("isQueen")).filter(_.kicked.equals("isKicked")).result
      .map(_.foreach(f => pw = game.setPiece(f._3, pb, Color.white, Queen.isQueen, Kicked.isKicked)))), Duration.Inf)

    //Games
    Await.result(database.run(gameTable.filter(_.name.equals("game1")).filter(_.lmc.equals("black")).result
      .map(_.foreach(f => lmc = Color.black))), Duration.Inf)

    Await.result(database.run(gameTable.filter(_.name.equals("game1")).filter(_.lmc.equals("white")).result
      .map(_.foreach(f => lmc = Color.white))), Duration.Inf)

    Await.result(database.run(gameTable.filter(_.name.equals("game1")).filter(_.winnerColor.equals("black")).result
      .map(_.foreach(f => winnerColor = Some(Color.black)))), Duration.Inf)

    Await.result(database.run(gameTable.filter(_.name.equals("game1")).filter(_.winnerColor.equals("white")).result
      .map(_.foreach(f => winnerColor = Some(Color.white)))), Duration.Inf)

    game = Game(board, pb, pw, lmc, winnerColor)
    game
  }
}
