package de.htwg.se.stratego.model

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class FigureSpec extends WordSpec with Matchers {

  "A Figure" when { "new" should {
    val p = Player("Test Player")
    val fig = new Figure.Bomb(p)

    "not be created when the strength is wrong" in {
      assertThrows[MatchError] {
        val f1 = Figure.withStrength(p, 200)
      }
    }

    "have the correct player" in {
      fig.player should be(p)
    }

    "return the name" in {
      fig.toString should be("B")
    }

    "return the player it belongs to" in {
      fig.player should be(p)
    }

    "have a correct constructor for Flag" in {
      val f1 = new Figure.Flag(p)
      f1.strength should be(0)
    }
    "have a correct constructor for Spy" in {
      val f1 = new Figure.Spy(p)
      f1.strength should be(1)
    }
    "have a correct constructor for Scout" in {
      val f1 = new Figure.Scout(p)
      f1.strength should be(2)
    }
    "have a correct constructor for Miner" in {
      val f1 = new Figure.Miner(p)
      f1.strength should be(3)
    }
    "have a correct constructor for Sergeant" in {
      val f1 = new Figure.Sergeant(p)
      f1.strength should be(4)
    }
    "have a correct constructor for Lieutenant" in {
      val f1 = new Figure.Lieutenant(p)
      f1.strength should be(5)
    }
    "have a correct constructor for Captain" in {
      val f1 = new Figure.Captain(p)
      f1.strength should be(6)
    }
    "have a correct constructor for Major" in {
      val f1 = new Figure.Major(p)
      f1.strength should be(7)
    }
    "have a correct constructor for Colonel" in {
      val f1 = new Figure.Colonel(p)
      f1.strength should be(8)
    }
    "have a correct constructor for General" in {
      val f1 = new Figure.General(p)
      f1.strength should be(9)
    }
    "have a correct constructor for Marshal" in {
      val f1 = new Figure.Marshal(p)
      f1.strength should be(10)
    }
    "have a correct constructor for Bomb" in {
      val f1 = new Figure.Bomb(p)
      f1.strength should be(11)
    }
  }}

  "A Figure" when { "created with a certain strength" should {
    val p = new Player("Test Player")

    "have a correct constructor for Flag" in {
      val f1 = Figure.withStrength(p, 0)
      f1.strength should be(0)
    }
    "have a correct constructor for Spy" in {
      val f1 = Figure.withStrength(p, 1)
      f1.strength should be(1)
    }
    "have a correct constructor for Scout" in {
      val f1 = Figure.withStrength(p, 2)
      f1.strength should be(2)
    }
    "have a correct constructor for Miner" in {
      val f1 = Figure.withStrength(p, 3)
      f1.strength should be(3)
    }
    "have a correct constructor for Sergeant" in {
      val f1 = Figure.withStrength(p, 4)
      f1.strength should be(4)
    }
    "have a correct constructor for Lieutenant" in {
      val f1 = Figure.withStrength(p, 5)
      f1.strength should be(5)
    }
    "have a correct constructor for Captain" in {
      val f1 = Figure.withStrength(p, 6)
      f1.strength should be(6)
    }
    "have a correct constructor for Major" in {
      val f1 = Figure.withStrength(p, 7)
      f1.strength should be(7)
    }
    "have a correct constructor for Colonel" in {
      val f1 = Figure.withStrength(p, 8)
      f1.strength should be(8)
    }
    "have a correct constructor for General" in {
      val f1 = Figure.withStrength(p, 9)
      f1.strength should be(9)
    }
    "have a correct constructor for Marshal" in {
      val f1 = Figure.withStrength(p, 10)
      f1.strength should be(10)
    }
    "have a correct constructor for Bomb" in {
      val f1 = Figure.withStrength(p, 11)
      f1.strength should be(11)
    }
  }}

  "A Figure" when { "tested for its strength" should {
    val p = Player("Test Player")
    val fig = new Figure.Bomb(p)

  }}

  "A Figure" when { "tested for its movability" should {
    val p = Player("Test Player")
    val fig = new Figure.Bomb(p)
    "return the correct movability" in {
      fig.isMovable should be(false)
    }
  }}

  "A Figure" when { "asked about its String representation" should {
    val p = Player("Test Player")
    val fig = new Figure.Major(p)

    "have the correct String" in {
      fig.toString should be(fig.strength.toString)
    }
    "have the correct String if it is a Flag" in {
      val f1 = new Figure.Flag(p)
      f1.toString should be("F")
    }
    "have the correct String if it is a Bomb" in {
      val f1 = new Figure.Bomb(p)
      f1.toString should be("B")
    }
  }}
}