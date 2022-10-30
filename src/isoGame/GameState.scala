package isoGame

import isoGame.blocks.Block
import isoGame.entities.{EditorPlayer, Entity, Player}
import GameState._
import isoGame.levels.Level

import java.io.{FileInputStream, FileOutputStream, ObjectInputStream, ObjectOutputStream}
import scala.collection.mutable.ArrayBuffer

class GameState(){
    var player: Player = null
    var level: Level = null
    
    def loadLevel(name: String): Unit = {
        level = Level.byName(name)
        level.initialize()
        terrain = level.terrain
        entityArray = level.entityArray
        chunkSize = level.chunkSize
        if(player == null) {
            if(IsoGame.editorMode) player = new EditorPlayer()
            else player = new Player(level.playerSpawn)
            level.spawnEntity(player)
        }
    }
    
    def updateBlocks(): Unit = {
        for(x <- 0 until GameState.chunkSize.x){
            for(y <- 0 until GameState.chunkSize.y){
                for(z <- 0 until GameState.chunkSize.z){
                    terrain(x, y, z).update(Point3(x, y, z))
                }
            }
        }
    }

    def updateEntities(): Unit = {
        for(e <- entityArray) e.update()

        //Sort so entities are rendered back to front
        entityArray.sortWith(_.pos.diagonalRow > _.pos.diagonalRow)
    }

    def saveState(): Unit = {
        val oosChunk = new ObjectOutputStream(new FileOutputStream("./levels/current.chunk"))
        oosChunk.writeObject(terrain)
        oosChunk.close()
        val oosEnt = new ObjectOutputStream(new FileOutputStream("./levels/current.entities"))
        oosEnt.writeObject(entityArray)
        oosEnt.close()
    }

    def loadState(): Unit = {
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
    var chunkSize: Point3 = Point3(16, 16, 32)
    var terrain: Array3[Block] = Array3[Block](GameState.chunkSize, blocks.Air)
    var entityArray: ArrayBuffer[Entity] = ArrayBuffer()

    def spawnEntity(e: Entity): Unit = {
        entityArray.append(e)
    }
}