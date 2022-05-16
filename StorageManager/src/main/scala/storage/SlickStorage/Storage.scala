package storage.SlickStorage

import storage.StorageTrait
import model.GameComponent.GameTrait

import slick.dbio.DBIO
import slick.lifted.TableQuery
import slick.jdbc.JdbcBackend
import slick.jdbc.JdbcBackend.Database
import slick.jdbc.PostgresProfile.api.*

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global

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

  override def load(): String = {
    val result = Await.result(database.run(boardTable.filter(_.id.equals(0)).result), Duration.Inf)
    result.head._2
  }
}
