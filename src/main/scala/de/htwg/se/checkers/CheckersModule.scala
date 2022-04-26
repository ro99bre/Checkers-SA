package de.htwg.se.checkers

import com.google.inject.AbstractModule
import de.htwg.se.checkers.control.ControllerComponent.ControllerTrait
import de.htwg.se.checkers.control.ControllerComponent.controllerBaseImpl
import de.htwg.se.checkers.control.ControllerComponent.controllerBaseImpl.Controller
import net.codingwell.scalaguice.ScalaModule
import de.htwg.se.checkers.model.GameComponent.GameBaseImpl
import de.htwg.se.checkers.model.GameComponent.GameTrait

class CheckersModule extends AbstractModule {

  override def configure(): Unit = {
    bind(classOf[GameTrait]).to(classOf[GameBaseImpl.Game])
    bind(classOf[ControllerTrait]).to(classOf[controllerBaseImpl.Controller])
  }
}
