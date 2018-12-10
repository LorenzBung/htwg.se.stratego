package de.htwg.se.stratego.view

import de.htwg.se.stratego.controller.GameEngine
import de.htwg.se.stratego.model._

import scala.collection.mutable.ListBuffer
import scalafx.Includes._
import scalafx.application.{JFXApp, Platform}
import scalafx.scene.Scene
import scalafx.scene.layout._
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.Label
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.input.{MouseButton, MouseDragEvent, MouseEvent}
import scalafx.scene.paint.Color._
import scalafx.scene.shape.{Rectangle, Shape}
import scalafx.scene.text.Font
import scalafx.stage.WindowEvent

object StrategoGUI extends JFXApp with Observer[Observable] {
  var board:GameBoard = GameEngine.board
  var engine:GameEngine = GameEngine.engine
  var gameBoardView = new GameBoardView(engine)
  var figureSelectionView = new FigureSelectionView(engine)

  var figureNameLabel:Label = new Label("Pick a Figure") {
    font = Font.font(30)
  }

  var playerNameLabel:Label = new Label("") {
    font = Font.apply(30)
    alignmentInParent = Pos.CenterRight
  }

  gameBoardView.loadBoard()

  engine.playerOne.addObserver(this)
  engine.playerTwo.addObserver(this)
  board.addObserver(this)

  stage = new JFXApp.PrimaryStage {
    title.value = "Stratego - SE Project - Lorenz Bung & Joshua Rutschmann"
    icons.add(new Image("stratego.png"))
    resizable = false
    scene = new Scene {
      fill = LightGray
      content = new VBox {
        alignment = Pos.Center
        children = Seq(new BorderPane {
          left = playerNameLabel
          right = figureNameLabel
          margin = Insets(10,50,0,50)
        }, gameBoardView,  figureSelectionView)
      }
    }
    onCloseRequest() = (_: WindowEvent) => { engine.exit() }
  }

  override def receiveUpdate(subject: Observable): Unit = {
    if(subject.isInstanceOf[GameBoard]){
      Platform.runLater {
        gameBoardView.loadBoard()
        figureSelectionView.updateFigures()

        if (engine.currentPlayer == engine.playerOne) {
          playerNameLabel.textFill = Red
        } else {
          playerNameLabel.textFill = Blue
        }

        playerNameLabel.text = engine.currentPlayer.name
      }
    } else if (subject.isInstanceOf[Player]) {
      var player = subject.asInstanceOf[Player]
      Platform.runLater {
        figureSelectionView.updateFigures()

        if (player.selectedFigure != null) {
          figureNameLabel.text = player.selectedFigure.description
        } else {
          figureNameLabel.text = "Pick a Figure"
        }
      }
    }
  }
}
