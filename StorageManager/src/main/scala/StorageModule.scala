package storage

import com.google.inject.AbstractModule
import model.GameComponent.GameTrait

class StorageModule extends AbstractModule {
  override def configure(): Unit = {
    //bind(classOf[StorageTrait]).to(classOf[FileStorage.Storage])
    //bind(classOf[StorageTrait]).to(classOf[SlickStorage.Storage])
    bind(classOf[StorageTrait]).to(classOf[MongoStorage.Storage])
    bind(classOf[GameTrait]).to(classOf[model.GameComponent.GameBaseImpl.Game])
  }
}
