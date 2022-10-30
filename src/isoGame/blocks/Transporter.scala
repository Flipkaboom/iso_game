package isoGame.blocks
import isoGame.{GameState, Point3}
import isoGame.entities.Player

/** @param destination the name of the new level to load
  * @param signature the signature of the destionation transporter
  */
class Transporter(val destination: String, val signature: Char) extends Block {
    val name: String = "Transporter"
    var collision: Boolean = false
    var transparent: Boolean = true
    var visible: Boolean = true

    /** The number of frames until the transporter becomes active again */
    var framesToActivation: Int = 0

    override def playerInside(p: Point3, player: Player): Unit = {
        if(framesToActivation > 0) {
            framesToActivation = 10
        }else {
            GameState.transport(destination, signature)
        }
    }

    override def update(p: Point3): Unit = {
        if(framesToActivation > 0) framesToActivation -= 1
    }
}
