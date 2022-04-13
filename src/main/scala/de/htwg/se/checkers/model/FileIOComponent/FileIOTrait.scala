package de.htwg.se.checkers.model.FileIOComponent

import de.htwg.se.checkers.model.GameComponent.GameTrait
import scala.util.Try

trait FileIOTrait {
  def load(): Try[GameTrait]
  def save(game: GameTrait): Try[Unit]
}
