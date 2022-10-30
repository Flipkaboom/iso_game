package isoGame

import processing.core.PApplet
import processing.event.KeyEvent

import java.awt.event.KeyEvent._
import scala.collection.mutable


class IsoGame extends Renderer{
    val gameInstance: GameLogic = new GameLogic

    val heldKeys: mutable.Set[Int] = mutable.Set()

    val keyBinds: Map[Int, String] = Map(
        (VK_W, "UP"),
        (VK_A, "LEFT"),
        (VK_S, "DOWN"),
        (VK_D, "RIGHT"),
        (VK_SPACE, "JUMP"),
        (VK_E, "INTERACT"),
        (VK_F, "FILL"),
        (VK_I, "COLORPICK"),
        (VK_P, "PRINT"),
        (VK_LEFT, "ARROWLEFT"),
        (VK_RIGHT, "ARROWRIGHT"),
        (VK_SHIFT, "SHIFT"),
        (VK_ENTER, "SAVE"),
        (VK_BACK_SPACE, "LOAD")
    )

    override def draw(): Unit = {
        gameInstance.updateGame()
        drawFrame(gameInstance.state)
    }

    override def keyPressed(event: KeyEvent): Unit = {
        val keyCode = event.getKeyCode
        if(keyBinds.contains(keyCode) && !heldKeys.contains(keyCode)) {
            heldKeys.addOne(keyCode)
            gameInstance.keyPressed(keyBinds(keyCode))
        }
    }

    override def keyReleased(event: KeyEvent): Unit = {
        val keyCode = event.getKeyCode
        if (keyBinds.contains(keyCode)) {
            heldKeys.remove(keyCode)
            gameInstance.keyReleased(keyBinds(keyCode))
        }
    }
}

object IsoGame{
    var startingLevel: String = "New"
    var newLevelName: String = "New"
    var editorMode: Boolean = false
    var editorSize: Point3 = Point3(16, 16, 32)

    def main(args: Array[String]): Unit = {
        startingLevel = args(0)
        PApplet.main("isoGame.IsoGame")
    }
}