package de.htwg.se.stratego.model

class GameBoard(fields: Array[Array[Field]]) extends Observable with Subject[Observable] {

  def set(coords: Coordinates, figure: Option[Figure]): Unit = {
    fields(coords.y - 1)(coords.x - 1).setFigure(figure)
    notifyObservers()
  }

  def this() {
    this(Array.tabulate(GameBoard.BoardSize, GameBoard.BoardSize)((x,y) => {
      val field = new Field()

      if (x == 4 || x == 5) y match {
        case 2 | 3 | 6 | 7 => field.lock()
        case _ =>
      }

      field
    }))
  }

  def get(coords: Coordinates): Field = {
    fields(coords.y - 1)(coords.x - 1)
  }

  def move(from: Coordinates, to: Coordinates): Unit = {
    set(to, Some(get(from).figure))
    set(from, None)
    notifyObservers()
  }

  def matrix: Array[Array[Field]] = {
    fields
  }

  override def toString: String = {
    var board = "   #"

    var i = 1

    for (i <- 1 to 10) {
      board += " %1$2d  |".format(i)
    }

    board += "\n"

    for (x <- 0 to 9) {
      board += "%1$2d # ".format(x + 1)
      for (y <- 0 to 9) {
        val f = fields(x)(y)
        board += "%1$2s ".format(f) + " | "
      }
      board += "\n"
    }

    board
  }
}

object GameBoard {
  val BoardSize = 10
}
