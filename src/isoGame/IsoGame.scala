package isoGame

import processing.core.PApplet

class IsoGame extends Renderer{
    val gameInstance: GameLogic = new GameLogic

    override def draw(): Unit = {
        gameInstance.updateGame()
        drawFrame(gameInstance.state)
    }
}

object IsoGame{
    def main(args: Array[String]): Unit = {
        PApplet.main("isoGame.IsoGame")
    }
}