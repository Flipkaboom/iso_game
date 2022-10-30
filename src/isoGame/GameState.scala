package isoGame

import isoGame.blocks.Block
import isoGame.entities.{EditorPlayer, Entity, Player}
import GameState._
import isoGame.levels.Level

import java.io.{FileInputStream, FileOutputStream, ObjectInputStream, ObjectOutputStream}
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

class GameState(){
    var player: Player = _
    var levelName: String = ""

    val savedLevels: mutable.Set[String] = mutable.Set()

    def checkTransport(): Unit = {
        if(!activateTransport) return

        val newEntityArray: ArrayBuffer[Entity] = ArrayBuffer()
        //level end method and removing unwanted entities
        for(e <- entityArray) {
            e.onLevelEnd()
            if(!e.endsWithLevel) newEntityArray.append(e)
        }
        entityArray = newEntityArray

        saveState()

        loadLevel(transportDest)
    }

    def loadLevel(name: String): Unit = {
        levelName = name
        if(savedLevels.contains(name)){
            loadState()
            spawnEntity(player)
        }
        else {
            val level = Level.byName(name)
            level.initialize()
            levelName = level.name
            terrain = level.terrain
            entityArray = level.entityArray
            chunkSize = level.chunkSize
            if (player == null) {
                if (IsoGame.editorMode) player = new EditorPlayer()
                else player = new Player(level.playerSpawn)
                level.spawnEntity(player)
            }else{
                spawnEntity(player)
            }
        }

        //Search for transporter with matching signature
        for (x <- 0 until GameState.chunkSize.x) {
            for (y <- 0 until GameState.chunkSize.y) {
                for (z <- 0 until GameState.chunkSize.z) {
                    terrain(x, y, z) match {
                        case b: blocks.Transporter =>
                            //Move to center of block if matching signature
                            if(b.signature == transportSignature) {
                                print("Signature found!")
                                b.framesToActivation = 10
                                player.pos = Point3Double(x + 0.5, y + 0.5, z)
                                println(player.pos.x + "," + player.pos.y + "," + player.pos.z)
                            }
                        case _ =>
                    }
                }
            }
        }
        activateTransport = false
        levelStart()
    }

    def levelStart(): Unit = {
        for (e <- GameState.entityArray) {
            e.onLevelStart()
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
        savedLevels.addOne(levelName)
        val oosChunk = new ObjectOutputStream(new FileOutputStream("./saves/" + levelName + ".chunk"))
        oosChunk.writeObject(terrain)
        oosChunk.close()
        val oosEnt = new ObjectOutputStream(new FileOutputStream("./saves/" + levelName + ".entities"))
        oosEnt.writeObject(entityArray)
        oosEnt.close()
    }

    def loadState(): Unit = {
        val oisChunk = new ObjectInputStream(new FileInputStream("./saves/" + levelName + ".chunk"))
        terrain = oisChunk.readObject.asInstanceOf[Array3[Block]]
        chunkSize = Point3(terrain.data.length, terrain.data(0).length, terrain.data(0)(0).length)
        oisChunk.close()
        val oisEnt = new ObjectInputStream(new FileInputStream("./saves/" + levelName + ".entities"))
        entityArray = oisEnt.readObject.asInstanceOf[ArrayBuffer[Entity]]
        oisEnt.close()
    }

    /** Saves only terrain to levels folder for use in level editor */
    def saveTerrain(): Unit = {
        val oosChunk = new ObjectOutputStream(new FileOutputStream("./levels/" + levelName + ".level"))
        oosChunk.writeObject(terrain)
        println("Saved to ./levels/" + levelName + ".level")
        oosChunk.close()
    }
}

object GameState {
    var chunkSize: Point3 = Point3(16, 16, 32)
    var terrain: Array3[Block] = Array3[Block](GameState.chunkSize, blocks.Air)
    var entityArray: ArrayBuffer[Entity] = ArrayBuffer()

    def spawnEntity(e: Entity): Unit = {
        entityArray.append(e)
    }

    var activateTransport: Boolean = false
    var transportDest: String = ""
    var transportSignature: Char = ' '
    def transport(destination: String, signature: Char): Unit = {
        activateTransport = true
        transportDest = destination
        transportSignature = signature
    }
}