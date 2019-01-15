package de.htwg.se.stratego.view

import de.htwg.se.stratego.controller.GameEngine
import de.htwg.se.stratego.model._
import scalafx.application.{JFXApp, Platform}
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control.{Button, Label}
import scalafx.scene.image.Image
import scalafx.scene.input.MouseEvent
import scalafx.scene.layout._
import scalafx.scene.paint.Color._
import scalafx.scene.text.Font
import scalafx.stage.WindowEvent

object StrategoGUI extends JFXApp with Observer[GameEngine] {
  var engine:GameEngine = GameEngine.engine
  var gameBoardView = new GameBoardView(engine)
  var figureSelectionView = new FigureSelectionView(engine)

  var figureNameLabel:Label = new Label("Pick a Figure") {
    font = Font.font(30)
  }

  var playerNameLabel:Label = new Label("") {
    font = Font.apply(30)
    alignmentInParent = Pos.CenterRight
    if (engine.gb.currentPlayer == engine.gb.playerOne) {
      textFill = Red
    } else {
      textFill = Blue
    }

    text = engine.gb.currentPlayer.name
  }

  gameBoardView.loadBoard()

  engine.addObserver(this)

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
          center = new HBox {
            alignment = Pos.Center
            children = Seq(new Button ("Load Game") {
              onMouseClicked = (_e: MouseEvent) => { engine.loadGame() }
            },new Button("Save Game") {
              onMouseClicked = (_e: MouseEvent) => { engine.saveGame() }
            }, new Button("New Game") {
              onMouseClicked = (_e: MouseEvent) => { engine.newGame() }
            })
          }
          right = figureNameLabel
          margin = Insets(10,50,0,50)
        }, gameBoardView,  figureSelectionView)
      }
    }
    onCloseRequest() = (_: WindowEvent) => { engine.exit() }
  }

  override def receiveUpdate(subject: GameEngine): Unit = {
      Platform.runLater {
        gameBoardView.loadBoard()
        figureSelectionView.updateFigures()

        if (engine.gb.currentPlayer == engine.gb.playerOne) {
          playerNameLabel.textFill = Red
        } else {
          playerNameLabel.textFill = Blue
        }

        playerNameLabel.text = engine.gb.currentPlayer.name

        if (engine.gb.currentPlayer.selectedFigure.isDefined) {
          figureNameLabel.text = engine.gb.currentPlayer.selectedFigure.get.description
        } else {
          figureNameLabel.text = "Pick a Figure"
        }
      }
  }
}
