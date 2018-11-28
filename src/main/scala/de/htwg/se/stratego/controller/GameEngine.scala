package de.htwg.se.stratego.controller

import de.htwg.se.stratego.model.{Coordinates, Field, Figure, GameBoard}

class GameEngine(gb: GameBoard) {
  def moveFigure(from: Coordinates, to: Coordinates): Boolean = {
    val field:Field = gb.get(from)

    //No Figure on Field "from"
    if (field.isEmpty) {
      return false
    }

    //Figure on the field is not movable
    if (field.figure.isMovable) {
      return false
    }

    //Blocked fields
    if (to.x == 4 || to.x == 5) {
      to.y match {
        case 3 | 4 | 7 | 8 => return false
      }
    }

    //Field is empty
    if (gb.get(to).isEmpty) {
      //TODO
    }
    true
  }
}
