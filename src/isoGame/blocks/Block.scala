package isoGame.blocks

import isoGame.entities.Player
import isoGame.{Point3, Renderer}
import processing.core.PImage

import scala.collection.mutable.ArrayBuffer

abstract class Block extends Serializable{
    val name: String
    var collision: Boolean
    var transparent: Boolean
    var visible: Boolean

    def update(p: Point3): Unit = {}

    def interact(p: Point3, player: Player): Unit = {}

    def texture: PImage = baseTexture

    @transient lazy val baseTexture: PImage = Renderer.textures(name)
}

object Block {
    val width = 16
    val height = 16

    def blockByName(name: String): Block = {
        name match {
            case "Air" => Air
            case "Dirt" => Dirt
            case "Grass" => Grass
            case "Stone" => Stone
            case "BlueBlinker" => new BlueBlinker
            case "RedBlinker" => new RedBlinker
            case "Vines" => Vines
            case "Pumpkin" => Pumpkin
            case "CarvedPumpkin" => CarvedPumpkin
            case "Pedestal" => Pedestal
            case "PickUpBlock" => new PickUpBlock
        }
    }

    val blockList: ArrayBuffer[String] = ArrayBuffer(
        "Air",
        "Dirt",
        "Grass",
        "Stone",
        "BlueBlinker",
        "RedBlinker",
        "Vines",
        "Pumpkin",
        "CarvedPumpkin",
        "Pedestal",
        "PickUpBlock"
    )
}