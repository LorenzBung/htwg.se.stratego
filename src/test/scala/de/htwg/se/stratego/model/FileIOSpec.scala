package de.htwg.se.stratego.model

import de.htwg.se.stratego.model.boardComponent.{Coordinates, Field, Figure, GameBoard}
import de.htwg.se.stratego.model.fileIoComponent._
import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import scala.io.Source

@RunWith(classOf[JUnitRunner])
class FileIOSpec extends WordSpec with Matchers {
  "A FileIO Instance" when { "new" should {
    val gb = new GameBoard()
    val p = Player("Test Player")
    gb.set(Coordinates(1, 1), Some(new Figure.Miner(p)))
    gb.playerOne.selectedFigure = Some(Figure.withStrength(gb.playerOne, Figure.BOMB))
    gb.playerTwo.selectedFigure = Some(Figure.withStrength(gb.playerTwo, Figure.BOMB))

    "save and load JSON correctly" when {
      new fileIoJsonImpl.FileIO().save(gb)
      val loadedBoard = new fileIoJsonImpl.FileIO().load.get

      "correct cells" in {
        loadedBoard.get(Coordinates(1, 1)).fig.get.strength should be (Figure.MINER)
      }

      "correct selection for player one" in {
        loadedBoard.playerOne.selectedFigure.get.strength should be (Figure.BOMB)
      }

      "correct selection for player two" in {
        loadedBoard.playerTwo.selectedFigure.get.strength should be (Figure.BOMB)
      }

    }

    "save and load XML correctly" when {
      new fileIoXmlImpl.FileIO().save(gb)
      val loadedBoard = new fileIoXmlImpl.FileIO().load.get

      "correct cells" in {
        loadedBoard.get(Coordinates(1, 1)).fig.get.strength should be (Figure.MINER)
      }

      "correct selection for player one" in {
        loadedBoard.playerOne.selectedFigure.get.strength should be (Figure.BOMB)
      }

      "correct selection for player two" in {
        loadedBoard.playerTwo.selectedFigure.get.strength should be (Figure.BOMB)
      }
    }
  }

  }


}
