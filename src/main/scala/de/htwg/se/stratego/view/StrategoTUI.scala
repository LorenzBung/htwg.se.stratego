package de.htwg.se.stratego.view

import de.htwg.se.stratego.controller.GameEngine
import de.htwg.se.stratego.model.{Coordinates, GameBoard, Player}

import scala.io.StdIn

class StrategoTUI {
  var engine: GameEngine = GameEngine.engine
  var board: GameBoard = GameEngine.board

  def main(args: Array[String]): Unit = {
    start()
  }

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

    mainLoop(board)
  }

  def printPrompt(player: Player): Unit ={
    var playerColor = if (engine.currentPlayer == engine.playerOne) Console.RED else Console.BLUE
    if (player.selectedFigure != null){
      print(playerColor + player.name + Console.RESET + " " + player.selectedFigure.description + " (" + player.remainingFigures(player.selectedFigure.strength) +" left)> " + Console.RESET)
    } else {
      print(playerColor + player.name + Console.RESET + "> ")
    }
  }


  def mainLoop(board: GameBoard) {
    print(Console.RED + Console.BOLD + "stratego> " + Console.RESET)

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
