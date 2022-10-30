package isoGame

import isoGame.blocks.Block
import isoGame.entities.{Entity, Player}

import scala.collection.mutable.ArrayBuffer

class GameState(){
    val terrain = Array3[Block](GameLogic.chunkSize, blocks.Air)
    val entityArray: ArrayBuffer[Entity] = ArrayBuffer()
    val player: Player = new Player(Point3Double(10, 8, 11))

    terrain.fillRect(Point3(0), Point3(15, 15, 9), blocks.Dirt)
    terrain.fillRect(Point3(0), Point3(15, 15, 5), blocks.Stone)
    terrain.fillRect(Point3(0, 0, 10), Point3(15, 15, 10), blocks.Grass)

    terrain.fillRect(Point3(0, 0, 0), Point3(1, 5, 15), blocks.Stone)

    terrain(Point3(0,0,10)) = blocks.Stone
    terrain(Point3(0,15,10)) = blocks.Stone
    terrain(Point3(15,0,10)) = blocks.Stone
    terrain(Point3(15,15,10)) = blocks.Stone
    terrain.fillRect(Point3(3,12,11), Point3(8,8,13), blocks.Dirt)
    terrain.fillRect(Point3(3, 3, 10), Point3(6, 3, 10), blocks.Stone)
    terrain.fillRect(Point3(3, 3, 12), Point3(6, 3, 12), blocks.Stone)

    spawnEntity(player)

    def spawnEntity(e: Entity): Unit = {
        entityArray.append(e)
    }

    def updateEntities(): Unit = {
        for(e <- entityArray) e.update(terrain)
    }
}
