package de.htwg.se.stratego.model

import org.junit.runner.RunWith
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class GameBoardSpec extends WordSpec with Matchers {

  "A GameBoard" when { "setting a figure" should {
    val gb = new GameBoard()
    val p = Player("Test Player")
    val fig = Some(new Figure.Miner(p))
    val coords = Coordinates(1, 1)
    "set it correctly" in {
      gb.set(coords, fig)
      gb.get(coords).fig should be(fig)
    }
   }}

  "A GameBoard" when { "asked for its matrix" should {
    val gb = new GameBoard()
    "return the correct matrix" in {
      gb.matrix.length should be(10)
    }
  }}

  "A GameBoard" when { "moving a figure" should {
    val gb = new GameBoard()
    val p = Player("Test Player")
    val fig = Some(new Figure.Miner(p))
    val from = Coordinates(1, 1)
    val to = Coordinates(1, 2)
    "move it correctly" in {
      gb.set(from, fig)
      gb.move(from, to)
      gb.get(to).fig should be(fig)
    }
  }}

  "A GameBoard" when { "tested for its String representation" should {
    val gb = new GameBoard()
    "have the correct String" in {
      gb.toString should be("   #  1  |  2  |  3  |  4  |  5  |  6  |  7  |  8  |  9  | 10  |" +
        "\n 1 #     |     |     |     |     |     |     |     |     |     | " +
        "\n 2 #     |     |     |     |     |     |     |     |     |     | " +
        "\n 3 #     |     |     |     |     |     |     |     |     |     | " +
        "\n 4 #     |     |     |     |     |     |     |     |     |     | " +
        "\n 5 #     |     |  X  |  X  |     |     |  X  |  X  |     |     | " +
        "\n 6 #     |     |  X  |  X  |     |     |  X  |  X  |     |     | " +
        "\n 7 #     |     |     |     |     |     |     |     |     |     | " +
        "\n 8 #     |     |     |     |     |     |     |     |     |     | " +
        "\n 9 #     |     |     |     |     |     |     |     |     |     | " +
        "\n10 #     |     |     |     |     |     |     |     |     |     | \n")
    }
  }}
}
