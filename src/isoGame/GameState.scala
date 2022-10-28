package isoGame

import isoGame.blocks.Block

class GameState(){
    val terrain = Array3[Block](GameLogic.chunkSize, blocks.Air)

    terrain.fillRect(Point3(0), Point3(15, 15, 9), blocks.Dirt)
    terrain.fillRect(Point3(0), Point3(15, 15, 5), blocks.Stone)
    terrain.fillRect(Point3(0, 0, 10), Point3(15, 15, 10), blocks.Grass)
}
