package de.htwg.se.stratego.model

class GameBoard(fields:Array[Array[Figure]]) {

  def setFieldAt(coords: Coordinates, fig:Figure): Unit = {
    fields(coords.x)(coords.y) = fig
  }

  def this() {
    this(Array.ofDim[Figure](GameBoard.BoardSize, GameBoard.BoardSize))
  }

  def get(coords: Coordinates): Figure = {
    fields(coords.x)(coords.y)
  }

  override def toString: String = {
    fields.map(_.mkString(" | ")).mkString("\n")
  }
}

object GameBoard {
  val BoardSize = 10
}