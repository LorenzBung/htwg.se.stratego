package de.htwg.se.stratego.controller

import com.google.inject.Guice
import de.htwg.se.stratego.StrategoModule
import de.htwg.se.stratego.model._
import net.codingwell.scalaguice.InjectorExtensions._
import de.htwg.se.stratego.model.boardComponent.{Coordinates, Field, Figure, GameBoardInterface}
import de.htwg.se.stratego.model.fileIoComponent.FileIOInterface
import de.htwg.se.stratego.view.{AlertView, StrategoGUI, StrategoTUI}
import scalafx.application.Platform
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType

class GameEngine extends Subject[GameEngine] {
  val injector = Guice.createInjector(new StrategoModule)
  var gb: GameBoardInterface = injector.instance[GameBoardInterface]

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

    val attacker = gb.get(from).fig.get

    if (!canMove(from, to) || !attacker.isMovable || attacker.player != gb.currentPlayer) return false

    if (gb.get(to).isEmpty) {
      gb.move(from, to)
      switchPlayers()
      return true
    }

    val defender = gb.get(to).fig.get

    // Attacker must be the current player, cannot beat own figures
    if (defender.player == gb.currentPlayer) return false

    var attackerWins = false
    var defenderWins = false

    if (attacker.strength == Figure.MINER && defender.strength == Figure.BOMB) {
      // Miner defuses Bomb
      attackerWins = true
    } else if (attacker.strength == Figure.SPY && defender.strength == Figure.MARSHAL) {
      // Spy kills Marshal
      attackerWins = true
    } else if (defender.strength == Figure.FLAG) {
      // Attacker wins game
      attackerWins = true
      println()
      println(attacker.player.name + " wins")
      new Alert(AlertType.Information, attacker.player.name + " wins").showAndWait()
      exit()
    } else if (attacker.strength > defender.strength) {
      // Attacker wins
      attackerWins = true
    } else if (defender.strength > attacker.strength){
      // Defender wins
      defenderWins = true
    } else if (defender.strength == attacker.strength) {
      // Figures have same strength
    }

    if(attackerWins){
      gb.move(from, to)
    } else if (defenderWins) {
      gb.move(to, from)
    } else {
      gb.set(from, None)
      gb.set(to, None)
    }


    Platform.runLater {
      StrategoGUI.stage.hide()
      new AlertView(attacker, !attackerWins, defender, !defenderWins).showAndWait()
      switchPlayers()
    }
    true
  }

  def canMove(from: Coordinates, to: Coordinates): Boolean = {
    val fig = gb.get(from).fig
    if(fig.isEmpty){
      false
    } else if(gb.get(to).fig.isDefined && gb.get(from).fig.get.player == gb.get(to).fig.get.player){
      false
    }else if (fig.get.strength == Figure.SCOUT) {
      canMoveScout(from, to)
    } else if (!fig.get.isMovable) {
      false
    } else {
      canMoveDefaultFigure(from, to)
    }
  }

  def canMoveDefaultFigure(from: Coordinates, to: Coordinates): Boolean = {
    val differenceX = Math.abs(from.x - to.x)
    val differenceY = Math.abs(from.y - to.y)
    differenceX + differenceY == 1 && !gb.get(to).isLocked
  }

  def canMoveScout(from: Coordinates, to: Coordinates): Boolean = {
    !gb.get(to).isLocked && !movesDiagonally(from, to) && !isFigureInBetween(from, to)
  }

  def movesDiagonally(from: Coordinates, to: Coordinates): Boolean = {
    from.x != to.x && from.y != to.y
  }

  def isFigureInBetween(from: Coordinates, to: Coordinates): Boolean = {
    var figureAmount = 0
    val xstep = if(from.x < to.x) 1 else -1
    for (i <- from.x + xstep until to.x by xstep) {
      if (!gb.get(Coordinates(i, from.y)).isEmpty || gb.get(Coordinates(i, from.y)).isLocked) {
        figureAmount += 1
      }
    }
    val ystep = if(from.y < to.y) 1 else -1
    for (i <- from.y + ystep until to.y by ystep) {
      if (!gb.get(Coordinates(from.x, i)).isEmpty || gb.get(Coordinates(from.x, i)).isLocked) {
        figureAmount += 1
      }
    }
    figureAmount > 0
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
      gb.currentPlayer.remainingFigures(field.fig.get.strength) += 1
      gb.set(coord, None)
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
    gb.set(coord, gb.currentPlayer.selectedFigure)
    gb.currentPlayer.placedFigure()
    notifyObservers()

    if(!gb.currentPlayer.hasUnplacedFigures){
      switchPlayers()
    }

    true
  }

  def canSet(coord:Coordinates): Boolean = {
    if (coord.x > gb.size || coord.x < 1 || coord.y > gb.size || coord.y < 1) {
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
      player.selectedFigure = Some(Figure.withStrength(player, strength))
      notifyObservers()
      return true
    }
    false
  }

  def newGame(): Unit = {
    gb = injector.instance[GameBoardInterface]
    notifyObservers()
  }

  def loadGame(): Unit = {
    gb = injector.instance[FileIOInterface].load.get
    notifyObservers()
  }

  def saveGame(): Unit = {
    injector.instance[FileIOInterface].save(gb)
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
