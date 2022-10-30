package isoGame.blocks

import isoGame.entities.Player
import isoGame.{Point3, Renderer}
import processing.core.PImage

import scala.collection.mutable.ArrayBuffer

//Allows the serializer to use older version of the class, can't set it to any other number cause then it can't
//load my level files :(
@SerialVersionUID(-3073395306560335385L)
abstract class Block extends Serializable{
    val name: String
    var collision: Boolean
    var transparent: Boolean
    var visible: Boolean

    def update(p: Point3): Unit = {}

    def steppedOn(p: Point3, player: Player): Unit = {}

    def playerInside(p: Point3, player: Player): Unit = {}

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
            //Only for looks, needs to be placed properly in level class builder
            case "Transporter" => new Transporter("", ' ')
            case "Sensor" => new Sensor
            case "Wire" => new Wire
            case "WireMirrored" => new WireMirrored
            case "WireVertical" => new WireVertical
            case "Junction" => new Junction
            case "AndGate" => new AndGate
            case "WireDoor" => new WireDoor
            case "Bricks" => new Bricks
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
        "PickUpBlock",
        "Transporter",
        "Sensor",
        "Wire",
        "WireMirrored",
        "WireVertical",
        "Junction",
        "AndGate",
        "WireDoor",
        "Bricks"
    )
}