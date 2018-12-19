package de.htwg.se.stratego.model.fileIoComponent.fileIoXmlImpl

import de.htwg.se.stratego.model.boardComponent.{Coordinates, Figure, GameBoard, GameBoardInterface}
import de.htwg.se.sudoku.model.fileIoComponent.FileIOInterface

import scala.xml.{Elem, XML}

class FileIO extends FileIOInterface {

  override def load: Option[GameBoardInterface] = {
    var grid: GameBoardInterface = new GameBoard()
    val file = XML.loadFile("grid.xml")
    //val injector = Guice.createInjector(new SudokuModule)

    val playerOne = file \\ "playerOne" \ "remaining" \ "figure"
    for (fig <- playerOne) {
      val strength: Int = (fig \ "@strength").text.toInt
      val count: Int = fig.text.trim.toInt
      grid.playerOne.remainingFigures.put(strength, count)
    }

    val playerTwo = file \\ "playerTwo" \ "remaining" \ "figure"
    for (fig <- playerOne) {
      val strength: Int = (fig \ "@strength").text.toInt
      val count: Int = fig.text.trim.toInt
      grid.playerTwo.remainingFigures.put(strength, count)
    }

    val cellNodes= file \\ "cell"
    for (cell <- cellNodes) {
      val row: Int = (cell \ "@row").text.toInt
      val col: Int = (cell \ "@col").text.toInt
      val strength: Int = cell.text.trim.toInt
      val isFirst: Boolean = (cell \ "@isFirst").text.toBoolean
      val player = if (isFirst) { grid.playerOne } else { grid.playerTwo }
      grid.set(Coordinates(row,col), Some(Figure.withStrength(player, strength)))
    }
    Some(grid)
  }

  def saveXML(grid:GameBoardInterface):Unit = {
    XML.save("grid.xml", gridToXml(grid))
  }

  def gridToXml(grid:GameBoardInterface):Elem = {
    var selected:Int = -1
    if (grid.playerOne.selectedFigure.isDefined) {
      selected = grid.playerOne.selectedFigure.get.strength
    }

    <grid>
      <currentPlayer>{(grid.playerOne == grid.currentPlayer).toString}</currentPlayer>
      <playerOne name={grid.playerOne.name.toString} selected={selected.toString}>
        <remaining>
        {
        for {
          (k,v) <- grid.playerOne.remainingFigures
        } yield figureToXml(k,v)
        }
        </remaining>
      </playerOne>
      <playerTwo name={grid.playerTwo.name.toString}>
        <remaining>
        {
        for {
          (k,v) <- grid.playerTwo.remainingFigures
        } yield figureToXml(k,v)
        }
      </remaining>
      </playerTwo>
      {
      for {
        row <- 1 to GameBoard.BOARDSIZE
        col <- 1 to GameBoard.BOARDSIZE
      } yield cellToXml(grid, Coordinates(row,col))
      }
    </grid>
  }

  def cellToXml(grid:GameBoardInterface, coordinates: Coordinates):Any = {
    if(grid.get(coordinates).fig.isDefined){
      val isFirst:Boolean = grid.get(coordinates).fig.get.player == grid.playerOne
        <cell row={coordinates.x.toString} col={coordinates.y.toString} isFirst={isFirst.toString}>
          {grid.get(coordinates).fig.get.strength.toString}
        </cell>
    }
  }

  def figureToXml(k:Int, v:Int):Elem = {
      <figure strength={k.toString}>
        {v.toString}
      </figure>
  }

  override def save(grid: GameBoardInterface): Unit = {

  }
}
