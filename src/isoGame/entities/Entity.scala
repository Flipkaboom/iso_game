package isoGame.entities

import com.jogamp.newt.event.MouseEvent.PointerType
import isoGame.blocks.Block
import isoGame.entities.Entity._
import isoGame.{Array3, GameLogic, Point3, Point3Double, Renderer}
import processing.core.PImage

abstract class Entity {
    val name: String
    var pos: Point3Double
    //hitbox size in pixels
    val width: Int
    val height: Int
    var speed: Point3Double
    val hasPhysics: Boolean

    lazy val texture: PImage = Renderer.textures(name)

    def frameActions(terrain: Array3[Block]): Unit = {}

    final def update(terrain: Array3[Block]): Unit = {
        move(speed, terrain)
//        println("spz" + speed.z + " z" + pos.z)
        //        if(hasPhysics) applyGravity()
        frameActions(terrain)
    }

    def accelerate(p: Point3Double): Unit = {
        speed = speed + p
    }

    def applyGravity(): Unit = {
        if(speed.z > -maxFallingSpeed){
            accelerate(Point3Double(0,0,-gravityAcceleration))
        }
    }

    def widthBlockScale: Double = width.toDouble / Block.width
    def heightBlockScale: Double = height.toDouble / Block.height

    def leftBound(p: Point3Double): Point3Double = p - Point3Double(widthBlockScale / 2, -(widthBlockScale / 2), 0)
    def rightBound(p: Point3Double): Point3Double = p + Point3Double(widthBlockScale / 2, -(widthBlockScale / 2), 0)

    def pointCollidesWithTerrain(p: Point3Double, terrain: Array3[Block]): Boolean = {
        terrain(p.toPoint3).collision ||
            p.x < 0 || p.y < 0 || p.z < 0 ||
            p.x >= GameLogic.chunkSize.x ||
            p.y >= GameLogic.chunkSize.y ||
            p.z >= GameLogic.chunkSize.z
    }

    def collidesWithTerrain(terrain: Array3[Block], p: Point3Double = pos): Boolean = {
        val p1: Point3Double = leftBound(p)
        val p2: Point3Double = rightBound(p)

        //flooring -0.x goes to 0 so this is checked before flooring
        if(p1.x < 0 || p1.y < 0 || p2.x < 0 || p2.y < 0) return true

        var currPoint: Point3 = p1.toPoint3

        //used to decide whether to snake up-down-up or down-up-down
        val startRowEven = p1.diagonalRow.toInt % 2
        while (currPoint != p2.toPoint3) {
            if (currPoint.x >= GameLogic.chunkSize.x ||
                currPoint.y >= GameLogic.chunkSize.y ||
                terrain(currPoint).collision){
                return true
            }
            //Depending on if current row is even snakes up or down
            if (currPoint.diagonalRow % 2 == startRowEven) currPoint = currPoint - Point3(0, 1, 0)
            else currPoint = currPoint + Point3(1, 0, 0)
        }

        if (currPoint.x >= GameLogic.chunkSize.x ||
            currPoint.y >= GameLogic.chunkSize.y ||
            terrain(currPoint).collision) {
            return true
        }
        else false
    }

    def move(dif: Point3Double, terrain: Array3[Block]): Unit = {
        if(speed.x.abs < 0.01 && speed.y.abs < 0.01 && speed.z.abs < 0.01) return

        val movedPos: Point3Double = pos + dif
        var finalPos: Point3Double = movedPos

        val collisionX: Boolean = collidesWithTerrain(terrain, pos.copy(x = movedPos.x))
        val collisionY: Boolean = collidesWithTerrain(terrain, pos.copy(y = movedPos.y))
        val collisionCombined: Boolean = collidesWithTerrain(terrain, pos.copy(x = movedPos.x, y = movedPos.y))

        val movedRightBound: Point3Double = rightBound(movedPos)
        val movedLeftBound: Point3Double = leftBound(movedPos)

        val touchingMargin: Double = 0.05
        val touchingCorner: Boolean = (
            (
                collidesWithTerrain(terrain, movedPos + Point3Double(touchingMargin, touchingMargin, 0)) ||
                collidesWithTerrain(terrain, movedPos + Point3Double(-touchingMargin, -touchingMargin, 0)) //||
//                collidesWithTerrain(terrain, movedPos + Point3Double(0, touchingMargin, 0)) ||
//                collidesWithTerrain(terrain, movedPos + Point3Double(0, -touchingMargin, 0))
            )
                &&
            (
                !pointCollidesWithTerrain(movedLeftBound + Point3Double(touchingMargin, touchingMargin, 0), terrain) &&
                !pointCollidesWithTerrain(movedLeftBound + Point3Double(-touchingMargin, -touchingMargin, 0), terrain) &&
//                !pointCollidesWithTerrain(movedLeftBound + Point3Double(0, touchingMargin, 0), terrain) &&
//                !pointCollidesWithTerrain(movedLeftBound + Point3Double(0, -touchingMargin, 0), terrain) &&
                !pointCollidesWithTerrain(movedRightBound + Point3Double(touchingMargin, touchingMargin, 0), terrain) &&
                !pointCollidesWithTerrain(movedRightBound + Point3Double(-touchingMargin, -touchingMargin, 0), terrain) //&&
//                !pointCollidesWithTerrain(movedRightBound + Point3Double(0, touchingMargin, 0), terrain) &&
//                !pointCollidesWithTerrain(movedRightBound + Point3Double(0, -touchingMargin, 0), terrain)
            )
        )
        if(touchingCorner) {
            println("corner")
            val horizontalSpeed: Double = (speed.x - speed.y) / 2
            val verticalSpeed: Double = (speed.x + speed.y) / 2
//            val verticalMargin = Point3Double(x = 0.01, y = 0.01)
            val verticalMargin = Point3Double(0)

            if(verticalSpeed > 0){
                pos = pos + Point3Double(horizontalSpeed, -horizontalSpeed, 0) - verticalMargin
                return
            }
            else if(verticalSpeed < 0){
                pos = pos + Point3Double(horizontalSpeed, -horizontalSpeed, 0) + verticalMargin
                return
            }

        }

//        //Moving into a corner between two blocks
//        if (collisionX && collisionY) {
//            return
//        }

        //Collision with anything
        if((collisionX || collisionY) && collisionCombined){
            if(collisionX) {
                if (movedPos.x < pos.x) {
                    finalPos = finalPos.copy(x = pos.x.floor + (widthBlockScale / 2))
                    println("x< " + pos.x + "," + pos.y + "new "+movedPos.x+","+movedPos.y)
                }
                else if (movedPos.x > pos.x) {
                    finalPos = finalPos.copy(x = (movedRightBound.x.floor - 0.001) - (widthBlockScale / 2))
                    println("x> " + pos.x + "," + pos.y + "new "+movedPos.x+","+movedPos.y)
                }
            }
            if(collisionY) {
                if (movedPos.y < pos.y) {
                    finalPos = finalPos.copy(y = pos.y.floor + (widthBlockScale / 2))
                    println("y< " + pos.x + "," + pos.y + "new "+movedPos.x+","+movedPos.y)
                }
                else if (movedPos.y > pos.y) {
                    finalPos = finalPos.copy(y = (movedLeftBound.y.floor - 0.001) - (widthBlockScale / 2))
                    println("y> " + pos.x + "," + pos.y + "new "+movedPos.x+","+movedPos.y)
                }
            }
        }
        //Collision with corner from the side
        else if(collisionCombined){
            if (movedPos.x < pos.x) finalPos = finalPos.copy(x = pos.x.floor + (widthBlockScale / 2))
            else if (movedPos.x > pos.x) {
                finalPos = finalPos.copy(x = (movedRightBound.x.floor - 0.001) - (widthBlockScale / 2))
            }
        }

        pos = finalPos

        //TODO: z?
    }
}

object Entity{
    val maxFallingSpeed = 0.1
    val gravityAcceleration = 0.01
}