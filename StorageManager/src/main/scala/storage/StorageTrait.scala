package storage

import model.GameComponent.GameTrait

trait StorageTrait {
  def save(game: String): Unit
  def load(): String
}
