package isoGame.blocks

import processing.core.PImage

object Air extends Block {
    val name = "Air"
    val collision: Boolean = false
    val transparent: Boolean = true
    val visible: Boolean = false
}
