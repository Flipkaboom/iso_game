package isoGame.entities

import isoGame.blocks.Block
import isoGame.{GameLogic, GameState, Point3, Point3Double, blocks}

class EditorPlayer()
    extends Player(Point3Double(0.99, 0.99, 0))
{
    override val name = "EditorPlayer"
    override val hasPhysics = false
    override val hasCollision = false
    override val width: Int = 16
    override val height: Int = 16

    var currBlockIndex: Int = 0
    var fillActive: Boolean = false
    var fillStartSet: Boolean = false
    var fillStart: Point3 = Point3(0, 0, 0)

    override def move(dif: Point3Double): Unit = {
        val newPos = pos + dif
        if(!pointCollides(newPos)) pos = newPos
    }

    def currBlock: String = Block.blockList(currBlockIndex)

    def nextBlock(): Unit = {
        currBlockIndex = (currBlockIndex + 1) % Block.blockList.length
        println(currBlock)
    }
    def prevBlock(): Unit = {
        currBlockIndex = Math.floorMod(currBlockIndex - 1, Block.blockList.length)
        println(currBlock)
    }

    def colorpick(): Unit = {
        val blockUnderCursor = GameState.terrain(pos.toPoint3)
        currBlockIndex = Block.blockList.indexOf(blockUnderCursor.name)
    }

    /** Print the code needed to manually place the current block at the current position
      * Can be used in buildLevel method in Level class */
    def printManualPlacementCode(): Unit = {
        val p = pos.toPoint3
        println("terrain(Point3("+ p.x + "," + p.y + "," + p.z + ")) = new " + currBlock + "()")
    }

    def toggleFill(): Unit = {
        fillActive = !fillActive
        fillStartSet = false
        println("Fill mode: " + fillActive)
    }

    override def interact(): Unit = {
        move(Point3Double(z = 1))
    }

    override def jump(): Unit = {
        if(fillActive){
            if(!fillStartSet) {
                fillStart = pos.toPoint3
                fillStartSet = true
            }
            else {
                GameState.terrain.fillRect(fillStart, pos.toPoint3, currBlock, Block.blockByName)
                fillStartSet = false
            }
        }
        else GameState.terrain(pos.toPoint3) = Block.blockByName(currBlock)
    }

    override def shift(): Unit = {
        move(Point3Double(z = -1))
    }

}
