package TextUI

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class CheckersSpec extends AnyWordSpec with Matchers {

  "The Checkers main class" should {
    "print Checkers board" in {
      Checkers.main(Array[String]("v"))
    }
  }
}
