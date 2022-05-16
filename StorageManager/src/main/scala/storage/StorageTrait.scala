package storage

import model.GameComponent.GameTrait

trait StorageTrait {
  def save(game: GameTrait): Unit
  def load(): GameTrait
}
