package isoGame

import isoGame.entities.{EditorPlayer, Player}
import processing.core.PApplet

import scala.collection.mutable.ArrayBuffer

class IsoEditor extends IsoGame {
    gameInstance.state.player = new EditorPlayer
    gameInstance.state.entityArray = ArrayBuffer()
    gameInstance.state.spawnEntity(gameInstance.state.player)
}

object IsoEditor{
    def main(args: Array[String]): Unit = {
        PApplet.main("isoGame.IsoEditor")
    }
}