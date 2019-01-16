package de.htwg.se.stratego.controller

import de.htwg.se.stratego.model.boardComponent.{Coordinates, GameBoard}
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class GameEngineSpec extends WordSpec with Matchers {
  "A GameEngine" when {
    val engine = new GameEngine()
    "exiting" should {
      "do it correctly" in {
        //TODO engine.exit() should be(())
      }
    }
    "it creates a new game" should {
      "create an empty board" in {
        engine.newGame()
        for (row <- 1 to engine.gb.size; col <- 1 to engine.gb.size){
          engine.get(Coordinates(row, col)).isEmpty should be(true)
        }
      }
    }
    "it saves and loads a game" should {
      "do it correctly" in {
        engine.selectFigure(3)
        engine.set(Coordinates(1, 10))
        val board = engine.gb
        engine.saveGame()
        engine.newGame()
        engine.loadGame()
        for (row <- 1 to engine.gb.size; col <- 1 to engine.gb.size){
          engine.get(Coordinates(row, col)).fig should be(board.get(Coordinates(row, col)).fig)
        }
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
        engine.get(Coordinates(1, 1)) should be(engine.gb.get(Coordinates(1, 1)))
      }
    }
    "asked about movement" should {
      engine.gb.currentPlayer = engine.gb.playerTwo
      engine.selectFigure(3)
      engine.set(Coordinates(1, 1))
      "return the correct value for diagonal movement" in {
        engine.movesDiagonally(Coordinates(1, 1), Coordinates(2, 2)) should be(true)
      }
      "return the correct value for scout movement" in {
        engine.canMoveScout(Coordinates(1, 1), Coordinates(1, 5)) should be(true)
      }
      "return the correct values for default movement" in {
        engine.canMoveDefaultFigure(Coordinates(1, 1), Coordinates(1, 2)) should be(true)
      }
      "return the correct value if a figure is in between moving vertically and it isn't" in {
        engine.isFigureInBetween(Coordinates(1, 1), Coordinates(1, 3)) should be(false)
      }
      "return the correct value if a figure is in between moving vertically" in {
        engine.gb.currentPlayer = engine.gb.playerTwo
        engine.selectFigure(3)
        engine.set(Coordinates(1, 1))
        engine.set(Coordinates(1, 2))
        engine.isFigureInBetween(Coordinates(1, 1), Coordinates(1, 3)) should be(true)
      }
      "return the correct value if a figure is in between moving horizontally and it isn't" in {
        engine.isFigureInBetween(Coordinates(1, 1), Coordinates(3, 1)) should be(false)
      }
      "return the correct value if a figure is in between moving horizontally" in {
        engine.set(Coordinates(2, 1))
        engine.isFigureInBetween(Coordinates(1, 1), Coordinates(3, 1)) should be(true)
      }
      "return the correct value if a figure can move" in {
        engine.unset(Coordinates(1, 2))
        engine.canMove(Coordinates(1, 1), Coordinates(1, 2)) should be(true)
      }
      "return the correct value if a figure can move and it is undefined" in {
        engine.canMove(Coordinates(5, 5), Coordinates(3, 3)) should be(false)
      }
      "return the correct value if a figure can move to an existing own figure" in {
        engine.set(Coordinates(2, 1))
        engine.canMove(Coordinates(1, 1), Coordinates(2, 1)) should be(false)
      }
      "return the correct value if a figure can move and it is a scout" in {
        engine.selectFigure(2)
        engine.set(Coordinates(2, 2))
        engine.canMove(Coordinates(2, 2), Coordinates(2, 3)) should be(true)
      }
      "return the correct value if a figure can move and it cannot move" in {
        engine.selectFigure(0)
        engine.unset(Coordinates(2, 1))
        engine.set(Coordinates(2, 1))
        engine.canMove(Coordinates(2, 1), Coordinates(2, 2)) should be(false)
      }
    }
  }
}