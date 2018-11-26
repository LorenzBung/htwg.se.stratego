package de.htwg.se.stratego

import de.htwg.se.stratego.model.Player

object YourGame {
  def main(args: Array[String]): Unit = {
    val student = Player("Joshua Rutschmann")
    println("Hello, " + student.name)
  }
}
