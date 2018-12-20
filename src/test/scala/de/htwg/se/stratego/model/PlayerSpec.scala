package de.htwg.se.stratego.model

import de.htwg.se.stratego.model.boardComponent.Figure
import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class PlayerSpec extends WordSpec with Matchers {

  "A Player" when { "new" should {
    val player = Player("Test Player")
    "have a name"  in {
      player.name should be("Test Player")
    }
    "have a nice String representation" in {
      player.toString should be("Test Player")
    }
  }}

  "A Player" when { "tested for its unplaced figures" should {
    val p = new Player("Test Player")
    "have some if he is new" in {
      p.hasUnplacedFigures should be(true)
    }
    "have none if all are placed" in {
      for (i <- 0 to 11) {
        p.remainingFigures(i) = 0
      }
      p.hasUnplacedFigures should be(false)
    }
  }}

  "A Player" when { "selecting a figure" should {
    val p = new Player("Test Player")
    "select the correct one" in {
      p.selectedFigure = Some(Figure.withStrength(p, 3))
      p.selectedFigure should be(Some(Figure.withStrength(p, 3)))
    }
  }}

  "A Player" when { "placing figures" should {
    val p = new Player("Test Player")
    p.selectedFigure = Some(Figure.withStrength(p, 8))
    "get one less if he has some left" in {
      p.placedFigure()
      p.remainingFigures(8) should be(1)
    }
    "still have none if he had none before" in {
      p.placedFigure()
      p.remainingFigures(8) should be(0)
    }
  }}
}
