package isoGame

import isoGame.blocks.Block
import isoGame.entities.{Entity, Player}
import GameState._

import java.io.{File, FileInputStream, FileOutputStream, ObjectInputStream, ObjectOutputStream}
import scala.collection.mutable.ArrayBuffer

class GameState(){
    var entityArray: ArrayBuffer[Entity] = ArrayBuffer()
    var player: Player = new Player(Point3Double(10, 8, 11))

    terrain.fillRect(Point3(0), Point3(15, 15, 9), blocks.Dirt)
    terrain.fillRect(Point3(0), Point3(15, 15, 5), blocks.Stone)
    terrain.fillRect(Point3(0, 0, 10), Point3(15, 15, 10), blocks.Grass)

    terrain.fillRect(Point3(0, 0, 0), Point3(1, 5, 15), blocks.Stone)
    terrain.fillRect(Point3(0, 0, 15), Point3(4, 1, 15), blocks.Grass)
    terrain.fillRect(Point3(7, 0, 15), Point3(8, 1, 15), "BlueBlinker", Block.blockByName)
    terrain.fillRect(Point3(11, 0, 15), Point3(12, 1, 15), "RedBlinker", Block.blockByName)

    terrain(Point3(0,0,10)) = blocks.Stone
    terrain(Point3(0,15,10)) = blocks.Stone
    terrain(Point3(15,0,10)) = blocks.Stone
    terrain(Point3(15,15,10)) = blocks.Stone
    terrain.fillRect(Point3(3,12,11), Point3(8,8,13), blocks.Dirt)
    terrain.fillRect(Point3(3, 3, 10), Point3(6, 3, 10), blocks.Stone)
    terrain.fillRect(Point3(3, 3, 12), Point3(6, 3, 12), blocks.Stone)

    spawnEntity(player)
    spawnEntity(new entities.Frog(Point3Double(12, 11.5, 15), Point3Double(x = 0.05)))

    def updateBlocks(): Unit = {
        for(x <- 0 until GameLogic.chunkSize.x){
            for(y <- 0 until GameLogic.chunkSize.y){
                for(z <- 0 until GameLogic.chunkSize.z){
                    terrain(x, y, z).update(Point3(x, y, z))
                }
            }
        }
    }

    def spawnEntity(e: Entity): Unit = {
        entityArray.append(e)
    }

    def updateEntities(): Unit = {
        for(e <- entityArray) e.update()

        //Sort so entities are rendered back to front
        entityArray.sortWith(_.pos.diagonalRow > _.pos.diagonalRow)
    }

    def saveChunkToFile(): Unit = {
        val oosChunk = new ObjectOutputStream(new FileOutputStream("./levels/current.chunk"))
        oosChunk.writeObject(terrain)
        oosChunk.close()
        val oosEnt = new ObjectOutputStream(new FileOutputStream("./levels/current.entities"))
        oosEnt.writeObject(entityArray)
        oosEnt.close()
    }

    def loadChunkFromFile(): Unit = {
        val oisChunk = new ObjectInputStream(new FileInputStream("./levels/current.chunk"))
        terrain = oisChunk.readObject.asInstanceOf[Array3[Block]]
        oisChunk.close()
        val oisEnt = new ObjectInputStream(new FileInputStream("./levels/current.entities"))
        entityArray = oisEnt.readObject.asInstanceOf[ArrayBuffer[Entity]]
        oisEnt.close()
        player = entityArray.filter{
            case p: Player => true
            case _ => false}
            .map{
            case p: Player => p
        }(0)
    }
}

object GameState {
    var terrain = Array3[Block](GameLogic.chunkSize, blocks.Air)
}