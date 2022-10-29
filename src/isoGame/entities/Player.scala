package isoGame.entities
import isoGame.Point3Double

//TODO: immutable?
class Player(var pos: Point3Double) extends Entity {
    val name: String = "Player"

    //TODO: change sizes to have better tolerances
    val width: Double = 16
    val height: Double = 32
}
