package isoGame

import isoGame.entities.Player

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

    def updateGame(): Unit = {
        state.updateEntities()
    }

    def keyPressed(key: String): Unit = {
        key match {
            case "UP" => state.player.accelerate(Point3Double(-2*Player.walkingSpeed, -2*Player.walkingSpeed, 0))
            case "LEFT" => state.player.accelerate(Point3Double(-Player.walkingSpeed, Player.walkingSpeed, 0))
            case "DOWN" => state.player.accelerate(Point3Double(2*Player.walkingSpeed, 2*Player.walkingSpeed, 0))
            case "RIGHT" => state.player.accelerate(Point3Double(Player.walkingSpeed, -Player.walkingSpeed, 0))
            case "JUMP" =>
                state.player.pos = Point3Double(10, 8, 11)
                state.player.speed = state.player.speed.copy(z = 0.8)
            case _ =>
        }
    }

    def keyReleased(key: String): Unit = {
        key match {
            case "UP" => state.player.accelerate(Point3Double(2*Player.walkingSpeed, 2*Player.walkingSpeed, 0))
            case "LEFT" => state.player.accelerate(Point3Double(Player.walkingSpeed, -Player.walkingSpeed, 0))
            case "DOWN" => state.player.accelerate(Point3Double(-2*Player.walkingSpeed, -2*Player.walkingSpeed, 0))
            case "RIGHT" => state.player.accelerate(Point3Double(-Player.walkingSpeed, Player.walkingSpeed, 0))
            case "JUMP" =>
            case _ =>
        }
    }

}

object GameLogic{
    val chunkSize: Point3 = Point3(16, 16, 16)
}