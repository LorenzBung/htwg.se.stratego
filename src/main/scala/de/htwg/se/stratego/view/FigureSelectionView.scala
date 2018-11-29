package de.htwg.se.stratego.view

import de.htwg.se.stratego.controller.GameEngine
import de.htwg.se.stratego.model.{Coordinates, Field, Figure}
import de.htwg.se.stratego.view.StrategoGUI._
import scalafx.geometry.Insets
import scalafx.Includes._
import scalafx.scene.image.ImageView
import scalafx.scene.layout._
import scalafx.scene.paint.Color
import scalafx.scene.paint.Color._

import scala.language.reflectiveCalls
import scala.collection.mutable.ListBuffer

class FigureSelectionView(engine: GameEngine) extends HBox {

  var figureSelection:ListBuffer[GridPane{def update():Unit}] = ListBuffer()

  for(j <- 0 until 12) {
    figureSelection += new GridPane {
      padding = Insets(5)
      var enabled = true
      update()

      def update(): Unit ={

        if(engine.currentPlayer.selectedFigure != null && engine.currentPlayer.selectedFigure.strength == j){
          var selectColor = if (engine.currentPlayer == engine.playerOne) Color.Red else Color.Blue
          background = new Background(Array(new BackgroundFill(selectColor, null, null)))
        } else {
          background = new Background(Array(new BackgroundFill(Transparent, null, null)))
        }

        if (engine.currentPlayer.remainingFigures(j) == 0) {
          enabled = false
          opacity = 0.5
          background = new Background(Array(new BackgroundFill(Transparent, null, null)))
        } else {
          enabled = true
          opacity = 1
        }

        children = new ImageView(j + ".png"){
          fitWidth = 60
          preserveRatio = true
        }
      }

      onMouseEntered = () => {
        if (enabled) {
          scaleX = 1.2
          scaleY = 1.2
        }
      }

      onMouseExited = () => {
        scaleX = 1
        scaleY = 1
      }

      onMouseClicked = () => {
        if (enabled){
          clearFigureSelection()
          engine.currentPlayer.selectFigure(j)
        }
      }
    }
  }

  padding = Insets(10)
  margin = Insets(10,20,0,20)
  spacing = 20
  children = figureSelection

  def clearFigureSelection(): Unit = {
    for (p <- figureSelection) {
      p.background = new Background(Array(new BackgroundFill(Transparent, null, null)))
    }
  }

  def updateFigures(): Unit = {
    for (p <- figureSelection) {
      p.update()
    }
  }
}