package isoGame

import isoGame.entities.{EditorPlayer, Player}

import scala.collection.mutable

//TODO: consider screen based level instead of moving camera
/*
        Camera:
            Pro:
                Smoother
                More impressive?? (Do I need that?)
                Probably nicer for gameplay
                Would work better for platformer style
            Con:
                Harder to implement (don't have much time)
                World needs to be continuous
        Screen:
            Pro:
                Way easier to implement
                Kinda cute ngl gives me retro vibes
                Would work better for puzzle/rpg style
                World can be small chunks
            Con:
                Might be annoying to tell where you are (exit right, enter left)
                How to handle height??
                Platforming challenges would need to be small (or chunks big)
 */

class GameLogic(){
    val state: GameState = new GameState

    //TODO: disable when editing
    def updateGame(): Unit = {
        state.updateEntities()
        state.updateBlocks()
    }

    def keyPressed(key: String): Unit = {
        key match {
            case "UP" => state.player.accelerate(Point3Double(-1*Player.walkingSpeed, -1*Player.walkingSpeed, 0))
            case "LEFT" => state.player.accelerate(Point3Double(-Player.walkingSpeed, Player.walkingSpeed, 0))
            case "DOWN" => state.player.accelerate(Point3Double(1*Player.walkingSpeed, 1*Player.walkingSpeed, 0))
            case "RIGHT" => state.player.accelerate(Point3Double(Player.walkingSpeed, -Player.walkingSpeed, 0))
            case "JUMP" => state.player.jump()
            case "INTERACT" => state.player.interact()
            case "SAVE" => state.saveChunkToFile()
            case "LOAD" => state.loadChunkFromFile()
            case _ =>
        }
        //Controls for editing
        state.player match {
            case p: EditorPlayer =>
                key match {
                    case "SHIFT" => p.shift()
                    case "ARROWLEFT" => p.prevBlock()
                    case "ARROWRIGHT" => p.nextBlock()
                    case "FILL" => p.toggleFill()
                    case _ =>
            }
            case _ =>
        }
    }

    def keyReleased(key: String): Unit = {
        key match {
            case "UP" => state.player.accelerate(Point3Double(1*Player.walkingSpeed, 1*Player.walkingSpeed, 0))
            case "LEFT" => state.player.accelerate(Point3Double(Player.walkingSpeed, -Player.walkingSpeed, 0))
            case "DOWN" => state.player.accelerate(Point3Double(-1*Player.walkingSpeed, -1*Player.walkingSpeed, 0))
            case "RIGHT" => state.player.accelerate(Point3Double(-Player.walkingSpeed, Player.walkingSpeed, 0))
            case _ =>
        }
    }

}

object GameLogic{
    val chunkSize: Point3 = Point3(16, 16, 32)
}