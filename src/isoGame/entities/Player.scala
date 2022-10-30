package isoGame.entities
import isoGame.blocks.Block
import isoGame.entities.Player.jumpStrength
import isoGame.{Array3, Point3Double}

class Player(var pos: Point3Double) extends Entity {
    val name: String = "Player"
    val hasPhysics: Boolean = true
    val hasCollision: Boolean = true
    /** hitbox width */
    val width: Int = 14
    /** hitbox height */
    val height: Int = 31
    var speed: Point3Double = Point3Double(0,0,0)

    def jump(): Unit = {
        if(onGround()) speed = speed.copy(z = jumpStrength)
    }

    def shift(): Unit = {}

    def interact(): Unit = {

    }
}

object Player{
    val walkingSpeed = 0.05
    val jumpStrength = 0.2
}
