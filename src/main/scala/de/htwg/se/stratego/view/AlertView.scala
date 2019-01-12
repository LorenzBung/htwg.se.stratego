package de.htwg.se.stratego.view
import de.htwg.se.stratego.model.boardComponent.Figure
import scalafx.animation.{FadeTransition, RotateTransition, StrokeTransition, TranslateTransition}
import scalafx.geometry.{Insets, Point3D, Pos}
import scalafx.scene.Scene
import scalafx.scene.control.Label
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.{FlowPane, HBox, VBox}
import scalafx.scene.paint.Color._
import scalafx.scene.shape.{Line, Rectangle}
import scalafx.scene.text.Font
import scalafx.scene.transform.Rotate
import scalafx.stage.Stage
import scalafx.util.Duration

class AlertView(attacker: Figure, attackerDies:Boolean, defender: Figure, defenderDies:Boolean) extends Stage {
  title.value = "Figure attacked"
  icons.add(new Image("stratego.png"))
  resizable = false

  val attackerImageView = new ImageView(url = attacker.strength + ".png")

  val attackAnimation: TranslateTransition = new TranslateTransition
  attackAnimation.setNode(attackerImageView)
  attackAnimation.setDuration(new Duration(1000))
  attackAnimation.setDelay(new Duration(1000))
  attackAnimation.setAutoReverse(true)
  attackAnimation.setToX(75)
  attackAnimation.setCycleCount(2)
  attackAnimation.play()

  val attackerLabel = new Label(attacker.player.name)
  attackerLabel.setFont(new Font("Arial", 25))

  val defenderImageView = new ImageView(url = defender.strength + ".png")

  if(attackerDies){
    val ft: FadeTransition = new FadeTransition
    ft.setNode(attackerImageView)
    ft.setDuration(new Duration(2000))
    ft.setDelay(new Duration(2000))
    ft.setFromValue(1.0)
    ft.setToValue(0.0)
    ft.setCycleCount(1)
    ft.play()
  }

  if(defenderDies){
    val ft: FadeTransition = new FadeTransition
    ft.setNode(defenderImageView)
    ft.setDuration(new Duration(2000))
    ft.setDelay(new Duration(2000))
    ft.setFromValue(1.0)
    ft.setToValue(0.0)
    ft.setCycleCount(1)
    ft.play()
  }

  val defenderLabel = new Label(defender.player.name)
  defenderLabel.setFont(new Font("Arial", 25))
  scene = new Scene {
    fill = LightGray
    content = new FlowPane() {
      hgap = 20.0
      padding = Insets(20,20,20,20)
      alignment = Pos.Center
      children = Seq(new VBox {
        alignment = Pos.Center
        children = Seq(attackerImageView, attackerLabel)
      }, new VBox {
        alignment = Pos.Center
        children = Seq(defenderImageView, defenderLabel)
      })
    }
  }
}