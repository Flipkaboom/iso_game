package isoGame.entities
import isoGame.blocks.Block
import isoGame.{Point3Double, Renderer}
import processing.core.PImage

//TODO: on level end delete self
class PlayerHat(var pos: Point3Double) extends Entity {
    val name: String = "PlayerHat"
    val hasPhysics: Boolean = false
    val hasCollision: Boolean = false
    /** hitbox width */
    val width: Int = 0
    /** hitbox height */
    val height: Int = 0
    var speed: Point3Double = Point3Double(0)

    override val endsWithLevel: Boolean = true

    visible = false

    var block: Block =  Block.blockByName("PickUpBlock")

    override def texture: PImage = {
        block.texture
    }
}
