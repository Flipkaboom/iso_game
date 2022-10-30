package isoGame.blocks
import isoGame.{GameState, Point3}
import isoGame.entities.Player

class PickUpBlock extends Block {
    val name: String = "PickUpBlock"
    var collision: Boolean = true
    var transparent: Boolean = false
    var visible: Boolean = true

    override def interact(p: Point3, player: Player): Unit = {
        if(player.wear(this)) GameState.terrain(p) = Air
    }
}
