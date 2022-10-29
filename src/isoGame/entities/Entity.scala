package isoGame.entities

import com.jogamp.newt.event.MouseEvent.PointerType
import isoGame.blocks.Block
import isoGame.{Array3, Point3, Point3Double, Renderer}
import processing.core.PImage

//TODO: should entities be immutable?
abstract class Entity {
    val name: String
    var pos: Point3Double
    val width: Double
    val height: Double

    lazy val texture: PImage = Renderer.textures(name)

    //TODO: collision using width and height
    //For a block with coords 0, 0, 0 the back lower corner is point 0, 0, 0 with the block extending to 1, 1, 1
    def collidesWithTerrain(terrain: Array3[Block], p: Point3Double = pos) : Boolean = {
        if(terrain(p.toPoint3).collision) true
        else false
    }

    def move(dif: Point3Double, terrain: Array3[Block]): Unit = {
        val movedPos: Point3Double = pos + dif
        var finalPos: Point3Double = movedPos

        val collisionX: Boolean = collidesWithTerrain(terrain, pos.copy(x = movedPos.x))
        val collisionY: Boolean = collidesWithTerrain(terrain, pos.copy(y = movedPos.y))
        val collisionCombined: Boolean = collidesWithTerrain(terrain, pos.copy(x = movedPos.x, y = movedPos.y))

        if(collisionX || collisionY){
            if(collisionX) {
                if (movedPos.x.floor < pos.x.floor) finalPos = finalPos.copy(x = pos.x.floor)
                else if (movedPos.x.floor > pos.x.floor) finalPos = finalPos.copy(x = movedPos.x.floor - 0.001)
                else println("x pos:" + pos.x + ", movedPos: " + movedPos.x)
            }
            if(collisionY) {
                if (movedPos.y.floor < pos.y.floor) finalPos = finalPos.copy(y = pos.y.floor)
                else if (movedPos.y.floor > pos.y.floor) finalPos = finalPos.copy(y = movedPos.y.floor - 0.001)
                else println("y pos:" + pos.x + ", movedPos: " + movedPos.x)
            }
        }
        else if(collisionCombined){
            if (movedPos.x.floor < pos.x.floor) finalPos = finalPos.copy(x = pos.x.floor)
            else if (movedPos.x.floor > pos.x.floor) finalPos = finalPos.copy(x = movedPos.x.floor)
        }


            pos = finalPos

        //TODO: z?
    }
}
