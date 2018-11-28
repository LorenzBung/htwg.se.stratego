package de.htwg.se.stratego.model

class GameBoard(fields:Array[Array[String]]) {

  def setFieldAt(f:(Int,Int), value:String): Unit = {
    fields(f._1)(f._2) = value
  }

  def this(){
    this(Array.ofDim[String](GameBoard.BoardSize, GameBoard.BoardSize))
  }

  override def toString: String = {
    fields.map(_.mkString(" | ")).mkString("\n")
  }
}

object GameBoard {
  val BoardSize = 10
}