package de.htwg.se.stratego.model.fileIoComponent

import de.htwg.se.stratego.model.boardComponent.GameBoardInterface


trait FileIOInterface {

  def load:Option[GameBoardInterface]
  def save(grid:GameBoardInterface):Unit

}
