package isoGame.levels

import isoGame.{Array3, GameState, Point3, Point3Double, blocks}
import isoGame.blocks.Block
import isoGame.entities.{Entity, Player}

import java.io.{FileInputStream, FileOutputStream, ObjectInputStream, ObjectOutputStream}
import scala.collection.mutable.ArrayBuffer

abstract class Level {
    var name: String
    val chunkSize: Point3
    val loadFromFile: Boolean

    var terrain: Array3[Block] = Array3[Block]()
    var entityArray: ArrayBuffer[Entity] = ArrayBuffer()

    val playerSpawn: Point3Double

    def initialize(): Unit = {
        if(loadFromFile) loadTerrain()
        else terrain = Array3(chunkSize, blocks.Air)
        buildLevel()
    }

    def buildLevel(): Unit

    def spawnEntity(e: Entity): Unit = {
        entityArray.append(e)
    }

    def saveTerrain(): Unit = {
        val oosChunk = new ObjectOutputStream(new FileOutputStream("./levels/" + name + ".level"))
        oosChunk.writeObject(terrain)
        println("Saved to ./levels/" + name + ".level")
        oosChunk.close()
    }

    def loadTerrain(): Unit = {
        val oisChunk = new ObjectInputStream(new FileInputStream("./levels/" + name + ".level"))
        terrain = oisChunk.readObject.asInstanceOf[Array3[Block]]
        GameState.terrain = terrain
        println("Loaded from ./levels/" + name + ".level")
        oisChunk.close()
    }
}

object Level{
    def byName(name: String) = {
        name match {
            case "Testing" => new Testing
            case "New" => new New
            case "FirstRoom" => new FirstRoom
            case "Blinker1" => new Blinker1
            case "PickUpBox1" => new PickUpBox1
        }
    }
}