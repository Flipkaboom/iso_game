package isoGame.blocks

import isoGame.{Point3, Renderer}
import processing.core.PImage

class BlueBlinker extends Block {
    val name: String = "BlueBlinker"
    var collision: Boolean = true
    var transparent: Boolean = false
    var visible: Boolean = true

    @transient lazy val offTexture: PImage = Renderer.textures("BlueBlinkerOff")

    override def texture: PImage = {
        if(transparent) offTexture
        else baseTexture
    }

    var counter: Int = 0

    override def update(p: Point3): Unit = {
        if(counter == 90){
            collision = !collision
            transparent = !transparent
            counter = 0
        }
        counter += 1
    }
}
