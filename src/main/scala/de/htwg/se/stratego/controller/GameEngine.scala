package de.htwg.se.stratego.controller

import de.htwg.se.stratego.model._
import de.htwg.se.stratego.model.boardComponent._
import de.htwg.se.stratego.view.{AlertView, StrategoGUI, StrategoTUI}
import scalafx.application.Platform
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType

class GameEngine extends Subject[GameEngine] {
  var gb: GameBoardInterface = new GameBoard()

  def exit(): Unit = {
    System.exit(0)
  }

  /**
    * Moves a Figure from a position to another.
    * @param from The current coords of the figure to be moved.
    * @param to The coords the figure is moved to.
    * @return False, if the figure can't be moved, otherwise true.
    */
  def move(from: Coordinates, to: Coordinates): Boolean = {
    // No figure on field "from" OR field locked
    if (gb.get(from).isEmpty || gb.get(to).isLocked) return false

    val attacker: Figure = gb.get(from).figure

    if (!canMove(from, to) || !attacker.isMovable || attacker.player != gb.currentPlayer) return false

    if (gb.get(to).isEmpty) {
      gb.move(from, to)
      switchPlayers()
      return true
    }

    val defender: Figure = gb.get(to).figure

    // Attacker must be the current player, cannot beat own figures
    if (defender.player == gb.currentPlayer) return false


    //Field is empty OR has figure of other player on it
    if (attacker.strength == Figure.MINER && defender.strength == Figure.BOMB) {
      // Miner defuses Bomb
      gb.move(from, to)
    } else if (attacker.strength == Figure.SPY && defender.strength == Figure.MARSHAL) {
      // Spy kills Marshal
      gb.move(from, to)
    } else if (defender.strength == Figure.FLAG) {
      // Attacker wins game
      gb.move(from, to)
      println()
      println(attacker.player.name + " wins")
      new Alert(AlertType.Information, attacker.player.name + " wins").showAndWait()
      exit()
    } else if (attacker.strength > defender.strength) {
      // Attacker wins
      gb.move(from, to)
    } else if (defender.strength > attacker.strength){
      // Defender wins
      gb.set(from, None)
    } else if (defender.strength == attacker.strength) {
      // Figures have same strength
      gb.set(from, None)
      gb.set(to, None)
    }

    Platform.runLater {
      StrategoGUI.stage.hide()
      new AlertView(attacker, defender).showAndWait()
      switchPlayers()
    }
    true
  }

  def canMove(from: Coordinates, to: Coordinates): Boolean = {
    // Can't move diagonally
    if (from.x != to.x && from.y != to.y) return false

    if (gb.get(from).figure.strength == Figure.SCOUT) {
      if (from.x < to.x) {
        for (between <- from.x + 1 until  to.x ){
          val f = gb.get(Coordinates(between, to.y))
          if (!f.isEmpty || f.isLocked) return false
        }
      } else if (from.x > to.x) {
        for (between <- to.x + 1 until from.x){
          val f = gb.get(Coordinates(between, to.y))
          if (!f.isEmpty || f.isLocked) return false
        }
      } else if (from.y < to.y) {
        for (between <- from.y + 1 until to.y){
          val f = gb.get(Coordinates(to.x, between))
          if (!f.isEmpty || f.isLocked) return false
        }
      } else if (from.y > to.y) {
        for (between <- to.y + 1 until from.y){
          val f = gb.get(Coordinates(to.x, between))
          if (!f.isEmpty || f.isLocked) return false
        }
      }
    } else if ((from.x > to.x + 1 || from.x < to.x - 1) || (from.y > to.y + 1 || from.y < to.y - 1)) {
      return false
    }

    true
  }

  def switchPlayers(): Unit = {
    if (gb.currentPlayer == gb.playerOne) {
      gb.currentPlayer = gb.playerTwo
    } else if (gb.currentPlayer == gb.playerTwo) {
      gb.currentPlayer = gb.playerOne
    }

    Platform.runLater {
      StrategoGUI.stage.hide()
      new Alert(AlertType.Information, "Please hand over to " + gb.currentPlayer.name).showAndWait()
      StrategoGUI.stage.show()
    }

    notifyObservers()
  }

  def unset(coord:Coordinates): Boolean = {
    val field = gb.get(coord)
    if(!field.isEmpty && !field.isLocked) {
      gb.set(coord, None)
      gb.currentPlayer.remainingFigures(field.figure.strength) += 1
      notifyObservers()
      true
    }
    false
  }

  def get(coord:Coordinates): Field = {
    gb.get(coord)
  }
  
  def set(coord:Coordinates): Boolean = {
    if (!canSet(coord)) return false
    gb.set(coord, Some(gb.currentPlayer.selectedFigure))
    gb.currentPlayer.placedFigure()
    notifyObservers()

    if(!gb.currentPlayer.hasUnplacedFigures){
      switchPlayers()
    }

    true
  }

  def canSet(coord:Coordinates): Boolean = {
    if (coord.x > GameBoard.BOARDSIZE || coord.x < 1 || coord.y > GameBoard.BOARDSIZE || coord.y < 1) {
      return false
    }

    if (gb.currentPlayer.selectedFigure == null){
      return false
    }

    if (gb.currentPlayer == gb.playerOne && coord.y < 7) {
      return false
    }

    if (gb.currentPlayer == gb.playerTwo && coord.y > 4) {
      return false
    }

    //Blocked fields
    if (gb.get(coord).isLocked || !gb.get(coord).isEmpty) {
      return false
    }

    true
  }

  def selectFigure(player: Player, strength: Int):Boolean = {
    if (strength <= Figure.BOMB && strength >= Figure.FLAG && player.remainingFigures(strength) != 0) {
      player.selectedFigure = Figure.withStrength(player, strength)
      notifyObservers()
      return true
    }
    false
  }
}

object GameEngine {
  var engine = new GameEngine()

  def main(args: Array[String]): Unit = {

    val tuiThread = new Thread {
      override def run() {
        new StrategoTUI().start()
      }
    }

    tuiThread.start()

    val guiThread = new Thread {
      override def run() {
        StrategoGUI.main(null)
      }
    }

    guiThread.start()
  }
}
