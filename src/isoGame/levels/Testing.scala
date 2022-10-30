package isoGame.levels
import isoGame.{Array3, GameState, Point3, Point3Double, blocks, entities}
import isoGame.blocks.Block
import isoGame.entities.{Entity, Player}

import scala.collection.mutable.ArrayBuffer

class Testing extends Level {
    var name: String = "Testing"
    val chunkSize: Point3 = Point3(16, 16, 32)
    val loadFromFile: Boolean = false
    override val playerSpawn: Point3Double = Point3Double(1,1,16)

    def buildLevel(): Unit = {
        terrain.fillRect(Point3(0), Point3(15, 15, 9), blocks.Dirt)
        terrain.fillRect(Point3(0), Point3(15, 15, 5), blocks.Stone)
        terrain.fillRect(Point3(0, 0, 10), Point3(15, 15, 10), blocks.Grass)

        terrain.fillRect(Point3(0, 0, 0), Point3(1, 5, 15), blocks.Stone)
        terrain.fillRect(Point3(0, 0, 15), Point3(4, 1, 15), blocks.Grass)
        terrain.fillRect(Point3(7, 0, 15), Point3(8, 1, 15), "BlueBlinker", Block.blockByName)
        terrain.fillRect(Point3(11, 0, 15), Point3(12, 1, 15), "RedBlinker", Block.blockByName)

        terrain(Point3(0, 0, 10)) = blocks.Stone
        terrain(Point3(0, 15, 10)) = blocks.Stone
        terrain(Point3(15, 0, 10)) = blocks.Stone
        terrain(Point3(15, 15, 10)) = blocks.Stone
        terrain.fillRect(Point3(3, 12, 11), Point3(8, 8, 13), blocks.Dirt)
        terrain.fillRect(Point3(3, 3, 10), Point3(6, 3, 10), blocks.Stone)
        terrain.fillRect(Point3(3, 3, 12), Point3(6, 3, 12), blocks.Stone)

        spawnEntity(new entities.Frog(Point3Double(12, 11.5, 15), Point3Double(x = 0.05)))
    }

}
