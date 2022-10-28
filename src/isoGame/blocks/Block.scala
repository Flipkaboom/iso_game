package isoGame.blocks

import isoGame.Renderer
import processing.core.PImage

abstract class Block {
    val name: String
    val collision: Boolean
    //TODO: remove if in the end all blocks are drawn anyway
    val transparent: Boolean
    val visible: Boolean

    lazy val texture: PImage = Renderer.textures(name)
}

object Block {
    val width = 16
    val height = 16
}

//TODO: Put blocks in here?
//TODO: Trait for types of blocks? (Functional, multi, visible/invisible)
//TODO: Object somwhere with "global" info like texture list