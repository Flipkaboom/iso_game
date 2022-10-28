package isoGame

import isoGame.Renderer.textures
import isoGame.blocks.Block
import processing.core.PImage

import java.io.File
import scala.collection.mutable

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

//        textures.addOne("Dirt" ,loadImage("assets/Dirt.png"))
    }

    def drawFrame(state: GameState): Unit = {
        updateRenderingScale()

        fill(255, 255, 255)
        rect(0, 0, width, height)

        for(p <- Point3(0) until GameLogic.chunkSize){
            if(state.terrain(p).visible) drawBlock(state.terrain(p), p)
        }
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

    //TODO: is height hard to see? Change in brightness with height?
    //TODO: don't draw background blocks (if needed for performance)
    def drawBlock(b: Block, p: Point3): Unit = {
        var x = 0
        var y = 0

        x += p.x * Block.width
        x -= p.diagonalRow() * (Block.width / 2)
        y += p.diagonalRow() * (Block.height / 4)
        y -= p.z * (Block.height / 2)

        x -= Block.width / 2
        y -= Block.height / 2

        x = (x * renderingScale).toInt
        y = (y * renderingScale).toInt

        x += width / 2
        y += height / 2

        val drawWidth = (Block.width * renderingScale).toInt
        val drawHeight = (Block.height * renderingScale).toInt

        image(b.texture, x, y, drawWidth, drawHeight)
    }


}

object Renderer {
    val textures: mutable.Map[String, PImage] = mutable.Map()
}