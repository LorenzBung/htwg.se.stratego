package de.htwg.se.stratego.view

import de.htwg.se.stratego.controller.GameEngine
import de.htwg.se.stratego.model._

import scala.io.StdIn

class StrategoTUI {
  var engine: GameEngine = GameEngine.engine
  var board: GameBoard = GameEngine.board

  def start() {
    println()
    println("""  _______/  |_____________ _/  |_  ____   ____   ____  """)
    println(""" /  ___/\   __\_  __ \__  \\   __\/ __ \ / ___\ /  _ \ """)
    println(""" \___ \  |  |  |  | \// __ \|  | \  ___// /_/  >  <_> )""")
    println("""/____  > |__|  |__|  (____  /__|  \___  >___  / \____/ """)
    println("""     \/                   \/          \/_____/         """)
    println()

    println("Available Figures:")
    println("0: Flag")
    println("1: Spy")
    println("2: Scout")
    println("3: Miner")
    println("4: Sergeant")
    println("5: Lieutenant")
    println("6: Captain")
    println("7: Major")
    println("8: Colonel")
    println("9: General")
    println("10: Marshal")
    println("11: Bomb")
    println()

    playerPreparation(engine.playerOne)
    playerPreparation(engine.playerTwo)

    mainLoop()
  }

  def playerPreparation(player:Player): Unit = {
    println("Hey " + player.name + "! Select a figure and place it somewhere " +
      "with \"select <STRENGTH>\" and \"set <X> <Y>\".")

    printPrompt(player)

    while (player.hasUnplacedFigures) {
      var line : String = ""
      if (!{line = StdIn.readLine(); line}.isEmpty) {
        val params = line.split(" ")
        params match {
          case Array("set", x, y) if x.forall(_.isDigit) && y.forall(_.isDigit) =>
            if (player.selectedFigure != null) {
              if (!engine.set(Coordinates(x.toInt, y.toInt))){
                println(Console.RED + "Couldn't place " + player.selectedFigure.description + Console.RESET)
              }
            } else {
              println("No Figure selected")
            }

          case Array("select", fig) if fig.forall(_.isDigit) =>
            val strength = fig.toInt
            if (!player.selectFigure(strength)) {
              println("You don't have any Figures of this strength")
            }
          case Array("show", _*) =>
            println(board)
          case Array("exit", _*) =>
            println(Console.RED + player.name + " surrendered" + Console.RESET)
            engine.exit()
          case _ =>
            println("Available Commands:")
            println("show, select <STRENGTH>, set <X> <Y>, exit")
        }
        printPrompt(player)
      }
    }
  }

  def printPrompt(player: Player): Unit ={
    var playerColor = if (engine.currentPlayer == engine.playerOne) Console.RED else Console.BLUE
    if (player.selectedFigure != null){
      print(playerColor + player.name + Console.RESET + " " + player.selectedFigure.description + " (" + player.remainingFigures(player.selectedFigure.strength) +" left)> " + Console.RESET)
    } else {
      print(playerColor + player.name + Console.RESET + "> ")
    }
  }

  def mainLoop(): Unit ={
    var line : String = ""
    while (!{line = StdIn.readLine(); line}.isEmpty) {
      val params = line.split(" ")

      params match {
        case Array("show", _*) =>
          println(board)

        case Array("move", a, b, c, d) =>
          val x1 = a.toInt
          val y1 = b.toInt
          val x2 = c.toInt
          val y2 = d.toInt
          engine.move(Coordinates(x1,y1), Coordinates(x2,y2))

        case Array("exit", _*) =>
          engine.exit()

        case _ =>
          println("Available Commands:")
          println("show, move <X1> <Y1> <X2> <Y2>, exit")
      }
      printPrompt(engine.currentPlayer)
    }
  }
}
