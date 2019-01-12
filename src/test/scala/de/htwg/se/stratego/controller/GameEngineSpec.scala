package de.htwg.se.stratego.controller

import de.htwg.se.stratego.model.Player
import de.htwg.se.stratego.model.boardComponent.{Coordinates, GameBoard}
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class GameEngineSpec extends WordSpec with Matchers {
  "A GameEngine" when {
    val gb = new GameBoard()
    val engine = new GameEngine()
    engine.gb = gb
    "exiting" should {
      "do it correctly" in {
        //TODO engine.exit() should be(())
      }
    }
    "asked to move a non-existing figure" should {
      "not accept the move" in {
        engine.move(Coordinates(1, 1), Coordinates(9, 9)) should be(false)
      }
    }
    "asked to make an impossible move" should {
      //TODO: Set Flag or something similar to (1, 1)
      "not accept the move" in {
        engine.move(Coordinates(1, 1), Coordinates(9, 9)) should be(false)
      }
    }
    /* TODO Test :D run later fucks up tests...
    "switching players" should {
      "switch correctly from p1 to p2" in {
        gb.currentPlayer = gb.playerOne
        engine.switchPlayers()
        gb.currentPlayer should be(gb.playerTwo)
      }
      "switch correctly from p2 to p1" in {
        gb.currentPlayer = gb.playerTwo
        engine.switchPlayers()
        gb.currentPlayer should be(gb.playerOne)
      }
    }
    */
    "removing a non-existing figure" should {
      "not be able to do so" in {
        engine.unset(Coordinates(9, 9)) should be(false)
      }
    }
    "asked for a certain field" should {
      "be the one the GameBoard returns" in {
        engine.get(Coordinates(1, 1)) should be(gb.get(Coordinates(1, 1)))
      }
    }
  }
}