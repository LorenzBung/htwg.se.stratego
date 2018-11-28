package de.htwg.se.stratego.view

import scala.io.StdIn

object StrategoTUI {

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

    mainLoop()
  }


  def mainLoop(): Unit ={
    var line : String = ""
    while (!{line = StdIn.readLine(); line}.isEmpty) {
      val params = line.split(" ")

      params match {
        case Array("clean", _*) =>
          println("Creating new Gameboard")

        case Array("test", a, b) =>
          println(a + " " + b)

        case Array("show", _*) =>
          println("null")

          case Array("move", a, b, c, d) =>
            val x1 = a.toInt
            val y1 = b.toInt
            val x2 = c.toInt
            val y2 = d.toInt
            //TODO

        case Array("exit", _*) =>
          sys.exit(0)

        case _ =>
          println("Available Commands:")
          println("show, move <X1> <Y1> <X2> <Y2>, exit")
      }
      print(Console.RED + Console.RESET + "> ")
    }
  }

}
