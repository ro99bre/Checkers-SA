package de.htwg.se.checkers

import com.google.inject.AbstractModule
import de.htwg.se.checkers.control.ControllerComponent.ControllerTrait
import de.htwg.se.checkers.control.ControllerComponent.controllerBaseImpl
import de.htwg.se.checkers.control.ControllerComponent.controllerBaseImpl.Controller
import de.htwg.se.checkers.model.FileIOComponent.FileIOTrait
import net.codingwell.scalaguice.ScalaModule
import de.htwg.se.checkers.model.GameComponent.GameBaseImpl
import de.htwg.se.checkers.model.GameComponent.GameTrait
import de.htwg.se.checkers.model.FileIOComponent.fileIoJsonImpl
import de.htwg.se.checkers.model.FileIOComponent.fileIoXmlImpl
import de.htwg.se.checkers.model.FileIOComponent.fileIoJsonFileChooserImpl
import de.htwg.se.checkers.model.FileIOComponent.fileIoJsonImpl.FileIO

class CheckersModule extends AbstractModule {

  override def configure(): Unit = {
    bind(classOf[GameTrait]).to(classOf[GameBaseImpl.Game])
    bind(classOf[ControllerTrait]).to(classOf[controllerBaseImpl.Controller])
    //bind(classOf[FileIOTrait]).to(classOf[fileIoXmlImpl.FileIO])
    bind(classOf[FileIOTrait]).to(classOf[fileIoJsonImpl.FileIO])
    //bind(classOf[FileIOTrait]).to(classOf[fileIoJsonFileChooserImpl.FileIO])
  }
}
