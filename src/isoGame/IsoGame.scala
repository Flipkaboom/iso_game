package isoGame

import processing.core.PApplet
import processing.event.KeyEvent

import java.awt.event.KeyEvent._


class IsoGame extends Renderer{
    val gameInstance: GameLogic = new GameLogic

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
        if(keyBinds.contains(event.getKeyCode)) {
            gameInstance.heldControls.addOne(keyBinds(event.getKeyCode))
        }
    }

    override def keyReleased(event: KeyEvent): Unit = {
        if (keyBinds.contains(event.getKeyCode)) {
            gameInstance.heldControls.remove(keyBinds(event.getKeyCode))
        }
    }
}

object IsoGame{
    def main(args: Array[String]): Unit = {
        PApplet.main("isoGame.IsoGame")
    }
}