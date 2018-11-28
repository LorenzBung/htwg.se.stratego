package de.htwg.se.stratego.controller

import de.htwg.se.stratego.model.{GameBoard, Coordinates, Figure}

class GameEngine(gb: GameBoard) {
  def moveFigure(from: Coordinates, to: Coordinates): Boolean = {
    val fig: Figure = gb.get(from)

    //No Figure on Field "from"
    if (fig.strength == -1) {
      return false
    }

    //Figure on the field is not movable
    if (fig.isMovable) {
      return false
    }

    //Blocked fields
    if (to.x == 4 || to.x == 5) {
      to.y match {
        case 3 | 4 | 7 | 8 => return false
      }
    }

    //Field is empty
    if (gb.get(to).strength == -1) {
      gb.setFieldAt(to, gb.get(from).strength)
      //TODO
    }
    true
  }
}
