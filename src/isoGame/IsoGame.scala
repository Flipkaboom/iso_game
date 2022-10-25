package isoGame

import processing.core.PApplet

class IsoGame extends Renderer{
    override def draw(): Unit = {
        rect(width/2, height/2, width/10-width/20, height/10-height/20)
    }
}

object IsoGame{
    def main(args: Array[String]): Unit = {
        PApplet.main("isoGame.IsoGame")
    }
}