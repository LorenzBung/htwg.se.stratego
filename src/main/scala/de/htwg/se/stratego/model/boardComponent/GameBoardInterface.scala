package de.htwg.se.stratego.model.boardComponent

import de.htwg.se.stratego.model.{Player, Subject}

trait GameBoardInterface {
  var playerOne:Player
  var playerTwo:Player
  var currentPlayer:Player
  val size:Int
  def get(coords: Coordinates): Field
  def move(from: Coordinates, to: Coordinates): Unit
  def set(coords: Coordinates, figure: Option[Figure]): Unit
}
