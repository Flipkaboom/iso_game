package isoGame

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

    val heldControls: mutable.Set[String] = mutable.Set()

    def updateGame(): Unit = {
        handlePlayerControls()
    }

    def handlePlayerControls(): Unit = {
        if(heldControls.contains("LEFT")) state.player.move(Point3Double(-0.1, 0.1, 0), state.terrain)
        if(heldControls.contains("RIGHT")) state.player.move(Point3Double(0.1, -0.1, 0), state.terrain)
        if(heldControls.contains("UP")) state.player.move(Point3Double(-0.1, -0.1, 0), state.terrain)
        if(heldControls.contains("DOWN")) state.player.move(Point3Double(0.1, 0.1, 0), state.terrain)
    }
}

object GameLogic{
    val chunkSize: Point3 = Point3(16, 16, 16)
}