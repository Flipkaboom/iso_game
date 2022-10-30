package isoGame.entities
import isoGame.Point3Double

class Frog(
            var pos: Point3Double,
            var speed: Point3Double
) extends Entity {
    val name: String = "Frog"
    val hasPhysics: Boolean = true
    val hasCollision: Boolean = true
    /** hitbox width */
    val width: Int = 14
    /** hitbox height */
    val height: Int = 14


    override def onWallCollision(): Unit = {
        speed = speed.copy(x = -speed.x, y = -speed.y)
    }
}
