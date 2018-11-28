package de.htwg.se.stratego.view

import de.htwg.se.stratego.model.{Coordinates, Figure, GameBoard}

import scala.io.StdIn

object StrategoTUI {
  var board = new GameBoard()

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

  def mainLoop(board: GameBoard) {
    print(Console.RED + Console.BOLD + "stratego> " + Console.RESET)

    var line : String = ""
    while (!{line = StdIn.readLine(); line}.isEmpty) {
      val params = line.split(" ")

      params match {
        case Array("clean", _*) =>
          println("Creating new GameBoard")

        case Array("test", a, b) =>
          println(a + " " + b)

        case Array("show") =>
          println(board)

        case Array("set", a, b) =>
          val x = a.toInt
          val y = b.toInt
          // TODO

        case Array("exit", _*) =>
          sys.exit(0)

        case _ =>
          println("Unrecognized Command")
      }

      print(Console.RED + Console.BOLD + "stratego> " + Console.RESET)
    }
  }

}
