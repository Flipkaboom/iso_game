package isoGame.entities
import isoGame.blocks.Block
import isoGame.{Array3, Point3Double}

//TODO: immutable?
class Player(var pos: Point3Double) extends Entity {
    val name: String = "Player"

    //TODO: change sizes to have better tolerances
    val width: Int = 14
    val height: Int = 32
    var speed: Point3Double = Point3Double(0,0,0)
    val hasPhysics: Boolean = false

    override def frameActions(terrain: Array3[Block]): Unit = {

    }

}

object Player{
    val walkingSpeed = 0.05
}
