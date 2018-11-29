package de.htwg.se.stratego.view

import de.htwg.se
import de.htwg.se.stratego
import de.htwg.se.stratego.controller.GameEngine
import de.htwg.se.stratego.model
import de.htwg.se.stratego.model._
import scalafx.application.{JFXApp, Platform}
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control.Label
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.{BorderPane, HBox, VBox}
import scalafx.scene.paint.Color.{Blue, LightGray, Red}
import scalafx.scene.text.Font
import scalafx.stage.WindowEvent

case class AlertView(attacker:Figure, defender:Figure) extends JFXApp {
  stage = new JFXApp.PrimaryStage {
    title.value = "Stratego - SE Project - Lorenz Bung & Joshua Rutschmann"

    //icons += new Image("stratego.png")
    resizable = false
    scene = new Scene {
      fill = LightGray
      content = new HBox {
        alignment = Pos.Center
        children = Seq(new ImageView(url = attacker.strength + ".png"), new ImageView(url = defender.strength + ".png"))
      }
    }
  }
}

object AlertView {
  AlertView(new model.Figure.Spy(new Player("Test")), new model.Figure.Spy(new Player("Player B")))
}