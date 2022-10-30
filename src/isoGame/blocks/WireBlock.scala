package isoGame.blocks

import isoGame.{GameState, Point3, Renderer}
import processing.core.PImage

@SerialVersionUID(646707016674611669L)
abstract class WireBlock extends Block {
    var handledThisFrame = false
    var powered: Boolean = false
    var calculatedNeighbours: Boolean = false
    val neighbourPlusX: Boolean
    val neighbourMinX: Boolean
    val neighbourPlusY: Boolean
    val neighbourMinY: Boolean
    val neighbourPlusZ: Boolean
    val neighbourMinZ: Boolean

    var neighbours: Set[Point3] = Set()

    def calculateNeighbours(): Unit = {
        if (neighbourPlusX) neighbours = neighbours + Point3(x = 1)
        if (neighbourMinX) neighbours = neighbours + Point3(x = -1)
        if (neighbourPlusY) neighbours = neighbours + Point3(y = 1)
        if (neighbourMinY) neighbours = neighbours + Point3(y = -1)
        if (neighbourPlusZ) neighbours = neighbours + Point3(z = 1)
        if (neighbourMinZ) neighbours = neighbours + Point3(z = -1)
    }

    @transient lazy val onTexture: PImage = Renderer.textures(name + "On")

    override def texture: PImage = {
        if (powered) onTexture
        else baseTexture
    }

    def power(pOrig: Point3, pDest: Point3): Unit = {
        if(handledThisFrame) return
        handledThisFrame = true
        if(!neighbours.contains(pOrig - pDest) && (pOrig != pDest)) return
        powered = true
        for(n <- neighbours) {
            val neighbourPos = pDest + n
            if (!(neighbourPos.x < 0 || neighbourPos.y < 0 || neighbourPos.z < 0 ||
                neighbourPos.x >= GameState.chunkSize.x || neighbourPos.y >= GameState.chunkSize.y ||
                neighbourPos.z >= GameState.chunkSize.z)) {
                GameState.terrain(neighbourPos) match {
                    case w: WireBlock => w.power(pDest, neighbourPos)
                    case _ =>
                }
            }
        }
    }

    def unPower(pOrig: Point3, pDest: Point3): Unit = {
        if(handledThisFrame) return
        handledThisFrame = true
        if(!neighbours.contains(pOrig - pDest) && (pOrig != pDest)) return
        powered = false
        for (n <- neighbours) {
            val neighbourPos = pDest + n
            if(!(neighbourPos.x < 0 || neighbourPos.y < 0 || neighbourPos.z < 0 ||
            neighbourPos.x >= GameState.chunkSize.x || neighbourPos.y >= GameState.chunkSize.y ||
            neighbourPos.z >= GameState.chunkSize.z)) {
                GameState.terrain(neighbourPos) match {
                    case w: WireBlock => w.unPower(pDest, neighbourPos)
                    case _ =>
                }
            }
        }
    }

    override def update(p: Point3): Unit = {
        handledThisFrame = false
        if(!calculatedNeighbours) {
            calculateNeighbours()
            calculatedNeighbours = true
        }
    }
}
