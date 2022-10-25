package isoGame

import isoGame.blocks.Block

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
    val terrain = Array3[Block](16, 16, 16, blocks.Air)
}
