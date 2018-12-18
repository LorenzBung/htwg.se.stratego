package de.htwg.se.stratego.model

import de.htwg.se.stratego.model.boardComponent.Coordinates
import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class CoordinatesSpec extends WordSpec with Matchers {

  "A Coordinate" when { "new" should {
    val coord = Coordinates(2, 3)
    "have the correct x value"  in {
      coord.x should be(2)
    }
    "have to correct y value" in {
      coord.y should be(3)
    }
  }}
}
