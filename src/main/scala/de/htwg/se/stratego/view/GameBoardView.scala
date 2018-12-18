package de.htwg.se.stratego.view

import de.htwg.se.stratego.controller.GameEngine
import de.htwg.se.stratego.model.boardComponent.{Coordinates, Field}
import scalafx.Includes._
import scalafx.geometry.Insets
import scalafx.scene.image.ImageView
import scalafx.scene.input.{MouseButton, MouseEvent}
import scalafx.scene.layout.{Background, BackgroundFill, BorderPane, GridPane}
import scalafx.scene.paint.Color

class GameBoardView(engine:GameEngine) extends GridPane {

  background = new Background(Array(new BackgroundFill(Color.Black, null, null)))
  hgap = 1
  vgap = 1
  minHeight = 811
  maxWidth = 811
  margin = Insets(10,0,0,0)

  var from:Coordinates = _

  def loadBoard(): Unit ={

    for (i <- 1 until 11) {
      for (j <- 1 until 11) {
        this.add(new BorderPane {
          padding = Insets(5)
          background = new Background(Array(new BackgroundFill(Color.LightGray, null, null)))
          minWidth = 80
          minHeight = 80

          val field:Field = engine.get(Coordinates(i,j))

          if (field.isLocked){
            background = new Background(Array(new BackgroundFill(Color.DarkGray, null, null)))
          } else {
            if(!field.isEmpty) {
              center = new BorderPane {

                center = new ImageView(if (field.figure.player == engine.gb.currentPlayer) field.figure.strength + ".png" else "stratego.png") {
                  fitWidth = 42
                  preserveRatio = true
                  smooth = true
                }
                maxWidth = 50
                if (field.figure.player == engine.gb.playerOne) {
                  style = "-fx-background-radius: 8px; -fx-background-color:red;"
                } else {
                  style = "-fx-background-radius: 8px; -fx-background-color:blue;"
                }
              }
            }
          }

          onMouseEntered = (_event: MouseEvent) => {
            if (engine.canSet(Coordinates(i,j))) {
              var hoverColor = if (engine.gb.currentPlayer == engine.gb.playerOne) Color.Red else Color.Blue
              background = new Background(Array(new BackgroundFill(hoverColor, null, null)))
            }
          }

          onMouseExited = (_event: MouseEvent) => {
            if (!field.isLocked && engine.gb.currentPlayer.hasUnplacedFigures) {
              background = new Background(Array(new BackgroundFill(Color.LightGray, null, null)))
            }
          }

          onMouseClicked = (event: MouseEvent) => {
            if (engine.gb.currentPlayer.hasUnplacedFigures){
              if (event.button == MouseButton.Primary) {
                engine.set(Coordinates(i,j))
              } else if (event.button == MouseButton.Secondary) {
                engine.unset(Coordinates(i, j))
              }
            } else {
              if (event.button == MouseButton.Primary) {
                if (from == null){
                  background = new Background(Array(new BackgroundFill(Color.Gold, null, null)))
                  from = Coordinates(i,j)
                } else {
                  engine.move(from, Coordinates(i,j))
                  from = null
                  loadBoard()
                }
              }
            }
          }

        }, i, j)
      }
    }
  }
}
