package isoGame.blocks

import processing.core.PImage

object Air extends Block {
    val name = "Air"
    val texture: PImage = null
    val collision: Boolean = false
    val transparent: Boolean = true
    val visible: Boolean = false
}
