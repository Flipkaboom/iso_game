package isoGame

import isoGame.Renderer.textures
import isoGame.blocks.Block
import isoGame.entities.Entity
import processing.core.PImage

import java.io.File
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

//TODO: abstract because it won't work on it's own?
class Renderer extends BaseEngine{
    var renderingScale: Float = 1

    override def settings(): Unit = {
        size(1280, 720)
        noSmooth()
    }

    override def setup(): Unit = {
        surface.setTitle("Iso")
        surface.setResizable(true)
//        frameRate(15)

        loadTextures()
    }

    def loadTextures(): Unit = {
        val folder = new File("assets")
        folder.listFiles(_.isFile).map(_.getPath).foreach(
            fileName => {
                textures.addOne(
                    fileName.substring(7, fileName.length - 4),
                    loadImage(fileName)
                )
            }
        )

    }

    def drawFrame(state: GameState): Unit = {
        updateRenderingScale()

        fill(255, 255, 255)
        rect(0, 0, width, height)

        drawWorld(state.terrain, state.entityArray)

//        for(e <- state.entityArray){
//            drawEntity(e)
//        }
    }

    def updateRenderingScale(): Unit = {
        val terrainWidth: Float = (GameLogic.chunkSize.x max GameLogic.chunkSize.y) * Block.width
        val terrainHeight: Float = {
            ((GameLogic.chunkSize.x max GameLogic.chunkSize.y) * Block.height / 2) +
                GameLogic.chunkSize.z * Block.height / 2
        }
        val scaleHor: Float = width / terrainWidth
        val scaleVert: Float = height / terrainHeight
        val scale = scaleHor min scaleVert
        //Multiplication of coordinates with renderingScale should always yield an integer number without
        //having to round otherwise blocks will not line up
        val smallestFactor = (Block.height / 2) min (Block.width / 4)
        renderingScale = ((scale * smallestFactor).floor) / smallestFactor
    }

    def screenPosFromCoords(p: Point3Double): PointDouble = {
        var x: Double = 0
        var y: Double = 0

        x += p.x * Block.width
        x -= p.diagonalRow * (Block.width / 2)
        y += p.diagonalRow * (Block.height / 4)
        y -= p.z * (Block.height / 2)

        x -= Block.width / 2
        y -= Block.height / 2

        PointDouble(x, y)
    }

    def drawTexture(img: PImage, p: PointDouble): Unit = {
        var x: Int = (p.x * renderingScale).toInt
        var y: Int = (p.y * renderingScale).toInt

        x += width / 2
        y += height / 2

        val drawWidth = (img.width * renderingScale).toInt
        val drawHeight = (img.height * renderingScale).toInt

        image(img, x, y, drawWidth, drawHeight)
    }

    //TODO: is height hard to see? Change in brightness with height?
    //TODO: don't draw background blocks (if needed for performance)
    def drawBlock(b: Block, p: Point3): Unit = {
        val screenpos = screenPosFromCoords(p.toPoint3Double)
        drawTexture(b.texture, screenpos)
    }

    def blockVisible(p: Point3, terrain: Array3[Block]): Boolean = {
        if(!terrain(p).visible) return false
        val blockUp = terrain(p + Point3(0,0,1))
        if(!blockUp.visible || blockUp.transparent) return true
        val blockLeft = terrain(p + Point3(0, 1, 0))
        if (!blockLeft.visible || blockLeft.transparent) return true
        val blockRight = terrain(p + Point3(1, 0, 1))
        if (!blockRight.visible || blockRight.transparent) return true
        false
    }

    def drawWorld(terrain: Array3[Block], entityArray: ArrayBuffer[Entity]): Unit = {
        for (diagRow <- 0 until GameLogic.chunkSize.x){
            for(z <- 0 until GameLogic.chunkSize.z){
                for(e <- entityArray){
                    if((e.pos.diagonalRow).toInt == diagRow && e.pos.z.toInt == z){
                        drawEntity(e)
                    }
                }
                for (i <- 0 to diagRow){
                    val p: Point3 = Point3(i, diagRow - i, z)
                    if(blockVisible(p, terrain)) drawBlock(terrain(p), p)
                }
            }
        }

        for (diagRow <- GameLogic.chunkSize.x to 0 by -1){
            for (z <- 0 until GameLogic.chunkSize.z){
                for (e <- entityArray) {
                    if (31 - (e.pos.diagonalRow).toInt == diagRow && e.pos.z.toInt == z) {
                        drawEntity(e)
                    }
                }
                for (i <- 0 until diagRow){
                    val p: Point3 = Point3(GameLogic.chunkSize.x - (diagRow - i), (GameLogic.chunkSize.x - 1) - i, z)
                    if (blockVisible(p, terrain)) drawBlock(terrain(p), p)
                }
            }
        }
    }

//    def sortEntitiesByRow(entityArray: ArrayBuffer[Entity]): ArrayBuffer[ArrayBuffer[Entity]] = {
//        val result: ArrayBuffer[ArrayBuffer[Entity]] = ArrayBuffer()
//        for(e <- entityArray){
//            e.pos.toPoint3.diagonalRow
//        }
//    }

    def drawEntity(e: Entity): Unit = {
        val screenPos = screenPosFromCoords(e.pos)
        val topLeftPos = screenPos - PointDouble(0, e.texture.height - (Block.height / 2))
        drawTexture(e.texture, topLeftPos)
    }


}

object Renderer {
    val textures: mutable.Map[String, PImage] = mutable.Map()
}