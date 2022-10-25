package isoGame.blocks

import processing.core.PImage

abstract class Block {
    val name: String
    val texture: PImage
    val collision: Boolean
    val transparent: Boolean
    val visible: Boolean
}