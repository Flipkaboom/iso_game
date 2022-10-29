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

    /** Checks if this entity collides with the given terrain
      * @param p optional point to check entity collision in a different position
      */
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

    /** Checks if moving to new position would intersect with a vertical corner and if so
      * handles the appropriate movement
      * @param movedPos the position the entity is moving to
      * @return true when corner intersection was found, otherwise false
      */
    def handleVerticalCorner(movedPos: Point3Double, terrain: Array3[Block]): Boolean = {
        val movedRightBound: Point3Double = rightBound(movedPos)
        val movedLeftBound: Point3Double = leftBound(movedPos)

        val horizontalMargin = Point3Double(x = 0.2, y = -0.2)
        val verticalMargin = Point3Double(x = 0.01, y = 0.01)

        val verticalSpeed: Double = (speed.x + speed.y) / 2

        val touchingCornerUp: Boolean = {
            verticalSpeed < 0 &&
            collidesWithTerrain(terrain, movedPos - verticalMargin) && //entity collides when moved up slightly
            (
                //Left and right bound moved up and outward don't collide
                !pointCollidesWithTerrain(movedLeftBound - verticalMargin - horizontalMargin, terrain) &&
                !pointCollidesWithTerrain(movedRightBound - verticalMargin + horizontalMargin, terrain)
            )
        }

        val touchingCornerDown: Boolean = {
            verticalSpeed > 0 &&
            collidesWithTerrain(terrain, movedPos + verticalMargin) && //entity collides when moved down slightly
            (
                //Left and right bound moved down and outward don't collide
                !pointCollidesWithTerrain(movedLeftBound + verticalMargin - horizontalMargin, terrain) &&
                !pointCollidesWithTerrain(movedRightBound + verticalMargin + horizontalMargin, terrain)
            )
        }

        val horizontalSpeed: Double = (speed.x - speed.y) / 2
        if (touchingCornerUp) {
            pos = pos + Point3Double(horizontalSpeed, -horizontalSpeed, 0)
            return true
        }
        else if (touchingCornerDown) {
            pos = pos + Point3Double(horizontalSpeed, -horizontalSpeed, 0)
            return true
        }

        false
    }

    def move(dif: Point3Double, terrain: Array3[Block]): Unit = {
        if(speed.x.abs < 0.01 && speed.y.abs < 0.01 && speed.z.abs < 0.01) return

        val movedPos: Point3Double = pos + dif
        var finalPos: Point3Double = movedPos

        //Check for vertical corners, if they were handled by the function nothing else needs to be done
        if(handleVerticalCorner(movedPos, terrain)) return

        val collisionNewPos: Boolean = collidesWithTerrain(terrain, movedPos)
        //move position valid
        if(!collisionNewPos) {
            pos = movedPos
            return
        }

        val collisionX: Boolean = collidesWithTerrain(terrain, pos.copy(x = movedPos.x, z = movedPos.z))
        val collisionY: Boolean = collidesWithTerrain(terrain, pos.copy(y = movedPos.y, z = movedPos.z))

        val movedRightBound: Point3Double = rightBound(movedPos)
        val movedLeftBound: Point3Double = leftBound(movedPos)

        //Collision with wall
        if((collisionX || collisionY) && collisionNewPos){
            if(collisionX) {
                if (movedPos.x < pos.x) {
                    finalPos = finalPos.copy(x = pos.x.floor + (widthBlockScale / 2))
//                    println("x< " + pos.x + "," + pos.y + "new "+movedPos.x+","+movedPos.y)
                }
                else if (movedPos.x > pos.x) {
                    finalPos = finalPos.copy(x = (movedRightBound.x.floor - 0.001) - (widthBlockScale / 2))
//                    println("x> " + pos.x + "," + pos.y + "new "+movedPos.x+","+movedPos.y)
                }
            }
            if(collisionY) {
                if (movedPos.y < pos.y) {
                    finalPos = finalPos.copy(y = pos.y.floor + (widthBlockScale / 2))
//                    println("y< " + pos.x + "," + pos.y + "new "+movedPos.x+","+movedPos.y)
                }
                else if (movedPos.y > pos.y) {
                    finalPos = finalPos.copy(y = (movedLeftBound.y.floor - 0.001) - (widthBlockScale / 2))
//                    println("y> " + pos.x + "," + pos.y + "new "+movedPos.x+","+movedPos.y)
                }
            }
        }
        //Collision with corner from the side
        else if(collisionNewPos){
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