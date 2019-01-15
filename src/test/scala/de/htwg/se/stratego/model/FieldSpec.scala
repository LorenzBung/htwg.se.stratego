package de.htwg.se.stratego.model

import de.htwg.se.stratego.model.boardComponent.{Coordinates, Field, Figure, GameBoard}
import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class FieldSpec extends WordSpec with Matchers {

  "A Field" when { "new" should {
    val p = Player("Test Player")
    val fig = new Figure.Bomb(p)
    val field = new Field(fig)
    "not be locked"  in {
      field.isLocked should be(false)
    }
    "set the right figure" in {
      field.fig.get should be(fig)
    }
  }}

  "A Field" when { "tested for Emptiness" should {
    val p = Player("Test Player")
    val fig = new Figure.Bomb(p)
    var field = new Field()
    "be empty if there is no figure on it" in {
      field.isEmpty should be(true)
    }
    "not be empty if there is a figure on it" in {
      field = new Field(fig)
      field.isEmpty should be(false)
    }
  }}

  "A Field" when { "tested for locking state" should {
    val gb = new GameBoard()
    "not be locked if on a unlocked field" in {
      gb.get(Coordinates(1, 2)).isLocked should be(false)
    }
    "be locked if in a locked position" in {
      gb.get(Coordinates(4, 6)).isLocked should be(true)
    }
  }}

  "A Field" when { "converted to a String" should {
    val p = Player("Test Player")
    val fig = new Figure.Bomb(p)
    var field = new Field(fig)
    "convert the strength of the figure to a string" in {
      field.toString should be("B")
    }
    "print a space if it is empty" in {
      field = new Field()
      field.toString should be(" ")
    }
    "print a block if it is locked" in {
      field.lock()
      field.toString should be("X")
    }
  }}
}
