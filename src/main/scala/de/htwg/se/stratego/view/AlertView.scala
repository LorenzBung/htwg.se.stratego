package de.htwg.se.stratego.view
import de.htwg.se.stratego.model.Figure.{Bomb, Spy}
import de.htwg.se.stratego.model._
import scalafx.geometry.Pos
import scalafx.scene.Scene
import scalafx.scene.control.Label
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.{BorderPane, HBox, VBox}
import scalafx.scene.paint.Color.{Blue, LightGray, Red}
import scalafx.stage.Stage

class AlertView(attacker: Figure, defender: Figure) extends Stage {
  title.value = "Figure attacked"
  width = 400
  height = 250
  icons.add(new Image("stratego.png"))
  resizable = false
  scene = new Scene {
    fill = LightGray
    content = new VBox() {

      alignment = Pos.Center
      children = Seq(new HBox {
        alignment = Pos.Center
        fill = Red
        children = Seq(new ImageView(url = attacker.strength + ".png"), new ImageView(url = defender.strength + ".png"))
      }, new HBox {
        alignment = Pos.Center
        fill = Red
        children = Seq(new Label(attacker.player.name), new Label(defender.player.name))
      })
    }
  }
}