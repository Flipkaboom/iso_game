package isoGame.blocks
import isoGame.Point3
import processing.core.PImage

@SerialVersionUID(2260316646202321493L)
class WireDoor extends WireBlock {
    val name: String = "WireDoor"
    var collision: Boolean = true
    var transparent: Boolean = true
    var visible: Boolean = true

    val neighbourPlusX: Boolean = false
    val neighbourMinX: Boolean = false
    val neighbourPlusY: Boolean = false
    val neighbourMinY: Boolean = false
    val neighbourPlusZ: Boolean = true
    val neighbourMinZ: Boolean = true

    override def texture: PImage = baseTexture

    override def update(p: Point3): Unit = {
        super.update(p)
        if(powered){
            collision = false
            visible = false
        }
        else{
            collision = true
            visible = true
        }
    }
}
