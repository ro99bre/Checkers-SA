package de.htwg.se.checkers.model.FileIOComponent.fileIoJsonImpl

import java.io.{File, PrintWriter}

import com.google.inject.Guice
import de.htwg.se.checkers.CheckersModule
import de.htwg.se.checkers.model.FileIOComponent.FileIOTrait
import de.htwg.se.checkers.model.GameComponent.GameBaseImpl.Piece
import de.htwg.se.checkers.model.GameComponent.GameTrait
import net.codingwell.scalaguice.InjectorExtensions._
import play.api.libs.json.{JsObject, JsValue, Json, Writes}

import scala.io.{BufferedSource, Source}

class FileIO extends FileIOTrait {

  override def load: GameTrait = {
    val source: BufferedSource = Source.fromFile("game.json")
    val sourceString : String = source.getLines().mkString
    val json : JsValue = Json.parse(sourceString)
    val injector = Guice.createInjector(new CheckersModule)
    var game : GameTrait = injector.instance[GameTrait]
    //loop over file, set game/board/pieces/colors...
    source.close()
    game
  }

  /*implicit val cellWrites = new Writes[CellTrait] {
    def writes(cell: CellTrait): JsObject = Json.obj(
      "y" -> cell.y,
      "x" -> cell.x,
      "cellColor" -> cell.color.toString//,//???
      //if (cell.piece.isDefined) "piece" -> cell.piece
      //"piece" -> cell.piece
      //implicit val for piece
    )
  }

  implicit val pieceWrites = new Writes[Piece] {
    def writes(piece: Piece): JsObject = Json.obj(
      "color" -> piece.color,
      "queen" -> piece.queen,
      "kicked" -> piece.kicked
    )
  }*/

/*
  def gameToJson(game: GameTrait): JsObject = {
    Json.obj(
      "game" -> Json.obj(
        "board" -> Json.toJson(
          for {
            y <- 0 until 8
            x <- 0 until 8
          } yield {
            Json.obj(
              "y" -> y,
              "x" -> x,
              "cell" -> Json.toJson(game.cell(y,x))//implicit val
            )
          }
        ),
        "pb" -> Json.toJson(//obj???
          for (index <- 0 until 12 )
            yield {
              Json.obj(
                "index" -> index,
                "piece" -> Json.toJson(game.getPB(index))//implicit val??
              )
            }
        ),
        "pw" -> Json.toJson(//obj???
          for (index <- 0 until 12 )
            yield {
              Json.obj(
                "index" -> index,
                "piece" -> Json.toJson(game.getPW(index))
              )
            }
        ) //,
        //"lmc" -> Json.toJson(game.getLastMoveColor()),//obj???
        //"winnercolor"-> Json.toJson(game.getWinnerColor())//obj???
      )
    )
  }*/

  override def save(game: GameTrait): Unit = {
    val pw = new PrintWriter(new File("game.json"))
    pw.write(Json.prettyPrint(gameToJson(game)))
    pw.close()
  }

  implicit val pieceWrites = new Writes[Option[Piece]] {
    def writes(piece: Option[Piece]): JsObject = Json.obj(
      //if (piece.isEmpty) "color" -> Json.toJson("None")
      //else {
        "color" -> Json.toJson(piece.get.color),
        "queen" -> Json.toJson(piece.get.queen),
        "kicked" -> Json.toJson(piece.get.kicked)
      //}
    )
  }

  def gameToJson(game: GameTrait) = {
    Json.obj(
      "game" -> Json.obj(
        "board" -> Json.obj(
          "cells" -> Json.toJson(//obj??? toJson???
            for {
              row <- 0 until 8;
              col <- 0 until 8
            } yield {
              Json.obj(
                "y" -> col,
                "x" -> row,
                "color" -> Json.toJson(game.cell(col,row).color),
                if (game.cell(col,row).piece.isEmpty) "piece" -> Json.toJson("None")
                else "piece" -> Json.toJson(game.cell(col,row).piece)//implicit Writes Option[Piece]
              )
            }
          )
        ),
        "pb" -> Json.toJson(//obj??? toJson???
          for {index <- 0 until 12} yield {
            Json.obj(
              "index" -> index,
              "color" -> Json.toJson(game.getPB()(index).color),
              "queen" -> Json.toJson(game.getPB()(index).queen),
              "kicked" -> Json.toJson(game.getPB()(index).kicked)
            )
          }
        ),
        "pw" -> Json.toJson(//obj??? toJson???
          for {index <- 0 until 12} yield {
            Json.obj(
              "index" -> index,
              "color" -> Json.toJson(game.getPW()(index).color),
              "queen" -> Json.toJson(game.getPW()(index).queen),
              "kicked" -> Json.toJson(game.getPW()(index).kicked)
            )
          }
        ),
        "lmc" -> Json.toJson(game.getLastMoveColor()),
        "winnerColor" -> Json.toJson(game.getWinnerColor())
      )
    )
  }
}
