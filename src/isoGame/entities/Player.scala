package isoGame.entities
import isoGame.blocks.Block
import isoGame.entities.Player.jumpStrength
import isoGame.{Array3, GameState, Point3Double, Renderer}
import processing.core.PImage

class Player(var pos: Point3Double) extends Entity {
    val name: String = "Player"
    val hasPhysics: Boolean = true
    val hasCollision: Boolean = true
    /** hitbox width */
    val width: Int = 14
    /** hitbox height */
    val height: Int = 30
    var speed: Point3Double = Point3Double(0,0,0)

    override val endsWithLevel: Boolean = true

    @transient lazy val headlessTexture: PImage = Renderer.textures("headlessPlayer")
    override def texture: PImage = {
        if(hat.visible) headlessTexture
        else baseTexture
    }

    var hat: PlayerHat = new PlayerHat(pos + Point3Double(-0.05, 0.05, heightBlockScale / 2.3))

    override def onLevelStart(): Unit = GameState.spawnEntity(hat)

    override def move(dif: Point3Double): Unit = {
        super.move(dif)
        hat.pos = pos + Point3Double(-0.05, 0.05, heightBlockScale / 2.3)
    }

    override def frameActions(): Unit = {
        val pDown = (pos - Point3Double(z = 1)).toPoint3
        if(onGround()) GameState.terrain(pDown).steppedOn(pDown, this)
        GameState.terrain(pos.toPoint3).playerInside(pos.toPoint3, this)
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
        if(GameState.terrain(pos.toPoint3).name != "Air") return
        GameState.terrain(pos.toPoint3) = hat.block
        hat.visible = false
        pos = pos + Point3Double(z = 1)
    }
}

object Player{
    val walkingSpeed = 0.05
    val jumpStrength = 0.2
}
