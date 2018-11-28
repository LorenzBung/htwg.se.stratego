package de.htwg.se.stratego.model

class GameBoard(fields:Array[Array[String]]) {

  def setFieldAt(f:(Int,Int), value:String): Unit = {
    fields(f._1)(f._2) = value
  }

  def this(){
    this(Array.ofDim[String](GameBoard.BoardSize, GameBoard.BoardSize))
  }

  override def toString: String = {
    var str = ""
    for (row <- fields){
      for (field <- row)
        str += field + " | "
      str += "\n"
    }
    str
  }
}

object GameBoard {
  val BoardSize = 10
}