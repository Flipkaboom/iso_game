package isoGame.blocks

import processing.core.PImage

object Air extends Block {
    val name = "Air"
    var collision: Boolean = false
    var transparent: Boolean = true
    var visible: Boolean = false
}
