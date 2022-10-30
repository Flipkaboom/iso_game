package isoGame.entities
import isoGame.blocks.Block
import isoGame.entities.Player.jumpStrength
import isoGame.{Array3, GameState, Point3Double}

class Player(var pos: Point3Double) extends Entity {
    val name: String = "Player"
    val hasPhysics: Boolean = true
    val hasCollision: Boolean = true
    /** hitbox width */
    val width: Int = 14
    /** hitbox height */
    val height: Int = 31
    var speed: Point3Double = Point3Double(0,0,0)

    var hat: PlayerHat = new PlayerHat(pos + Point3Double(z = heightBlockScale / 1.8))

    override def onLevelStart(): Unit = GameState.spawnEntity(hat)

    override def move(dif: Point3Double): Unit = {
        super.move(dif)
        hat.pos = pos + Point3Double(z = heightBlockScale / 1.8)
    }

    def jump(): Unit = {
        if(onGround()) speed = speed.copy(z = jumpStrength)
    }

    def shift(): Unit = {}

    def interact(): Unit = {
        if(hat.visible) unWear()
        else {
            val pDown = (pos - Point3Double(z = 1)).toPoint3
            GameState.terrain(pDown).interact(pDown, this)
        }
    }

    def wear(b: Block): Boolean = {
        if(hat.visible) return false
        else if(!onGround()) return false
        else {
            hat.visible = true
            hat.block = b
            return true
        }
    }

    def unWear(): Unit = {
        if(!onGround()) return
        if(collides(pos + Point3Double(z = 1))) return
        GameState.terrain(pos.toPoint3) = hat.block
        hat.visible = false
        pos = pos + Point3Double(z = 1)
    }
}

object Player{
    val walkingSpeed = 0.05
    val jumpStrength = 0.2
}
