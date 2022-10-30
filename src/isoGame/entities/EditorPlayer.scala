package isoGame.entities

import isoGame.blocks.Block
import isoGame.{GameLogic, GameState, Point3, Point3Double, blocks}

class EditorPlayer()
    //Initialize at half of each chunk size
    extends Player(Point3Double(0, 0, 14.8))
{
    override val name = "EditorPlayer"
    override val hasPhysics = false
    override val hasCollision = false
    override val width: Int = 0
    override val height: Int = 0

    var currBlockIndex: Int = 0
    var fillActive: Boolean = false
    var fillStartSet: Boolean = false
    var fillStart: Point3 = Point3(0, 0, 0)

    def currBlock: String = Block.blockList(currBlockIndex)

    def nextBlock(): Unit = {
        currBlockIndex = (currBlockIndex + 1) % Block.blockList.length
        println(currBlock)
    }
    def prevBlock(): Unit = {
        currBlockIndex = Math.floorMod(currBlockIndex - 1, Block.blockList.length)
        println(currBlock)
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
                fillStart = pos.toPoint3 + Point3(z = 1)
                fillStartSet = true
            }
            else {
                GameState.terrain.fillRect(fillStart, pos.toPoint3 + Point3(z = 1), currBlock, Block.blockByName)
                fillStartSet = false
            }
        }
        else GameState.terrain(pos.toPoint3 + Point3(z = 1)) = Block.blockByName(currBlock)
    }

    override def shift(): Unit = {
        move(Point3Double(z = -1))
    }

}
