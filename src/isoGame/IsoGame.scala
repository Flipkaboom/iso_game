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
        (VK_SPACE, "JUMP")
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
    def main(args: Array[String]): Unit = {
        PApplet.main("isoGame.IsoGame")
    }
}