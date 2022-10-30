package isoGame.entities

import com.jogamp.newt.event.MouseEvent.PointerType
import isoGame.blocks.Block
import isoGame.entities.Entity._
import isoGame.{Array3, GameLogic, GameState, Point3, Point3Double, Renderer}
import processing.core.PImage


//TODO: subclasses for different categories
abstract class Entity extends Serializable{
    val name: String
    val hasPhysics: Boolean
    val hasCollision: Boolean
    /** hitbox width */
    val width: Int
    /** hitbox height */
    val height: Int
    /** Used for when an entity shoudl be deleted on level end */
    val endsWithLevel: Boolean = false

    var visible: Boolean = true

    var pos: Point3Double
    var speed: Point3Double

    def texture: PImage = baseTexture

    @transient lazy val baseTexture: PImage = Renderer.textures(name)

    def frameActions(): Unit = {}

    def onWallCollision(): Unit = {}

    def onLevelStart(): Unit = {}

    def onLevelEnd(): Unit = {}

    final def update(): Unit = {
        move(speed)
        if(hasPhysics) applyGravity()
        frameActions()
    }

    def accelerate(p: Point3Double): Unit = {
        speed = speed + p
    }

    def applyGravity(): Unit = {
        if(speed.z > -maxFallingSpeed){
            accelerate(Point3Double(0,0,-gravityAcceleration))
        }
    }

    def onGround(): Boolean = {
        pos.z % 1 < 0.0001 && collides(pos - Point3Double(z = 1))
    }

    /** @return width of the entity converted to block units (1 block length = 1 unit) */
    def widthBlockScale: Double = width.toDouble / Block.width
    /** @return height of the entity converted to block units (1 block height = 1 unit) */
    def heightBlockScale: Double = height.toDouble / (Block.height / 2)

    /** @return the leftmost point of the collision plane */
    def leftBound(p: Point3Double): Point3Double = p - Point3Double(widthBlockScale / 2, -(widthBlockScale / 2), 0)
    /** @return the rightmost point of the collision plane */
    def rightBound(p: Point3Double): Point3Double = p + Point3Double(widthBlockScale / 2, -(widthBlockScale / 2), 0)

    /** Checks if given point collides with the terrain */
    def pointCollides(p: Point3Double): Boolean = {
        p.x < 0 || p.y < 0 || p.z < 0 ||
        p.x >= GameState.chunkSize.x ||
        p.y >= GameState.chunkSize.y ||
        p.z >= GameState.chunkSize.z ||
        (GameState.terrain(p.toPoint3).collision && hasCollision)
    }

    /** checks if the blocks from the given point up to the given point + entity height
      * collides with terrain */
    def pointWithHeightCollides(p: Point3Double): Boolean = {
        val minZ = p.z.toInt
        val maxZ = (p.z + heightBlockScale).toInt
        for(z <- minZ to maxZ){
            if(pointCollides(p.copy(z = z))) return true
        }
        false
    }

    /** Checks if this entity collides with the terrain
      * @param p optional point to check entity collision in a different position */
    def collides(p: Point3Double = pos): Boolean = {
        val p1: Point3Double = leftBound(p)
        val p2: Point3Double = rightBound(p)

        //flooring -0.x goes to 0 so this is checked before flooring
        if(pointWithHeightCollides(p1) || pointWithHeightCollides(p2)) return true

        var currPoint: Point3Double = p1

        //used to decide whether to snake up-down-up or down-up-down
        val startRowEven = p1.diagonalRow.toInt % 2
        while (currPoint.toPoint3 != p2.toPoint3) {
            if (pointWithHeightCollides(currPoint)){
                return true
            }
            //Depending on if current row is even snakes up or down
            if (currPoint.toPoint3.diagonalRow % 2 == startRowEven) currPoint = currPoint - Point3Double(0, 1, 0)
            else currPoint = currPoint + Point3Double(1, 0, 0)
        }

        if (pointWithHeightCollides(currPoint)) {
            return true
        }
        else false
    }

    /** Checks if moving to new position would intersect with a vertical corner and if so
      * handles the appropriate movement
      * @param movedPos the position the entity is moving to
      * @return true when corner intersection was found, otherwise false */
    def handleVerticalCorners(movedPos: Point3Double): Boolean = {
        var movedRightBound: Point3Double = rightBound(movedPos)
        var movedLeftBound: Point3Double = leftBound(movedPos)

        val verticalSpeed: Double = (speed.x + speed.y) / 2
        val horizontalSpeed: Double = (speed.x - speed.y) / 2

        val horizontalMargin = Point3Double(x = 0.2, y = -0.2)
        //Vertical margin in direction entity is moving in
        val verticalMargin = {
            if(verticalSpeed < 0) Point3Double(x = -0.01, y = -0.01)
            else if(verticalSpeed > 0) Point3Double(x = 0.01, y = 0.01)
            else Point3Double(0)
        }

        //Add horizontal margin to bound entity is moving away from to prevent getting stuck on corners
        if(horizontalSpeed < 0){
            movedRightBound = movedRightBound + horizontalMargin
        }else if(horizontalSpeed > 0){
            movedLeftBound = movedLeftBound - horizontalMargin
        }

        val touchingCorner: Boolean = {
            verticalSpeed != 0 &&
            collides(movedPos + verticalMargin) && //entity collides when moved up/down slightly
            (
                //Left and right bound moved up/down and outward don't collide
                !pointWithHeightCollides(movedLeftBound + verticalMargin) &&
                !pointWithHeightCollides(movedRightBound + verticalMargin)
            )
        }

        if (touchingCorner) {
            //Change movement into only horizontal movement
            pos = pos + Point3Double(horizontalSpeed, -horizontalSpeed, 0)
            return true
        }

        false
    }

    def handleZMovement(movedPos: Point3Double): Unit = {
        val movedPosOnlyZ: Point3Double = pos.copy(z = movedPos.z)
        if(collides(movedPosOnlyZ)){
            speed = speed.copy(z = 0)
            if(movedPos.z > pos.z){
                pos = pos.copy(z = movedPos.z.floor + (1 - (heightBlockScale % 1)) - 0.001)
            }else if(movedPos.z < pos.z){
                pos = pos.copy(z = pos.z.floor)
            }
        }else{
            pos = pos.copy(z = movedPos.z)
        }
    }

    def move(dif: Point3Double): Unit = {
        var movedPos: Point3Double = pos + dif

        handleZMovement(movedPos)
        movedPos = movedPos.copy(z = pos.z)

        val collisionNewPos: Boolean = collides(movedPos)
        //move position valid
        if(!collisionNewPos) {
            pos = movedPos
            return
        }

        //Check for vertical corners, if they were handled by the function nothing else needs to be done
        if(handleVerticalCorners(movedPos)) {
            onWallCollision()
            return
        }

        val collisionX: Boolean = collides(pos.copy(x = movedPos.x, z = movedPos.z))
        val collisionY: Boolean = collides(pos.copy(y = movedPos.y, z = movedPos.z))

        val movedRightBound: Point3Double = rightBound(movedPos)
        val movedLeftBound: Point3Double = leftBound(movedPos)

        var finalPos: Point3Double = movedPos

        //Collision with wall
        if(collisionX || collisionY){
            if(collisionX) {
                if (movedPos.x < pos.x) {
                    finalPos = finalPos.copy(x = pos.x.floor + (widthBlockScale / 2))
                }
                else if (movedPos.x > pos.x) {
                    finalPos = finalPos.copy(x = (movedRightBound.x.floor - 0.001) - (widthBlockScale / 2))
                }
            }
            if(collisionY) {
                if (movedPos.y < pos.y) {
                    finalPos = finalPos.copy(y = pos.y.floor + (widthBlockScale / 2))
                }
                else if (movedPos.y > pos.y) {
                    finalPos = finalPos.copy(y = (movedLeftBound.y.floor - 0.001) - (widthBlockScale / 2))
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

        onWallCollision()
        pos = finalPos
    }
}

object Entity{
    val maxFallingSpeed = 0.5
    val gravityAcceleration = 0.008
}