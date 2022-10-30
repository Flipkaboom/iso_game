package isoGame

import isoGame.entities.{EditorPlayer, Player}

class GameLogic(){
    val state: GameState = new GameState()
    state.loadLevel(IsoGame.startingLevel)

    def updateGame(): Unit = {
        if(IsoGame.editorMode) return
        state.updateEntities()
        state.updateBlocks()
        state.checkTransport()
    }

    def keyPressed(key: String): Unit = {
        if(!IsoGame.editorMode)
            key match {
                case "UP" => state.player.accelerate(Point3Double(-1*Player.walkingSpeed, -1*Player.walkingSpeed, 0))
                case "LEFT" => state.player.accelerate(Point3Double(-Player.walkingSpeed, Player.walkingSpeed, 0))
                case "DOWN" => state.player.accelerate(Point3Double(1*Player.walkingSpeed, 1*Player.walkingSpeed, 0))
                case "RIGHT" => state.player.accelerate(Point3Double(Player.walkingSpeed, -Player.walkingSpeed, 0))
                case "JUMP" => state.player.jump()
                case "INTERACT" => state.player.interact()
//                case "SAVE" => state.saveState()
//                case "LOAD" => state.loadState()
                case _ =>
            }
        else
            state.player match {
                case p: EditorPlayer =>
                    key match {
                        case "UP" => p.move(Point3Double(y = -1))
                        case "LEFT" => p.move(Point3Double(x = -1))
                        case "DOWN" => p.move(Point3Double(y = 1))
                        case "RIGHT" => p.move(Point3Double(x = 1))
                        case "JUMP" => p.jump()
                        case "INTERACT" => p.interact()
                        case "SHIFT" => p.shift()
                        case "ARROWLEFT" => p.prevBlock()
                        case "ARROWRIGHT" => p.nextBlock()
                        case "FILL" => p.toggleFill()
                        case "SAVE" => state.saveTerrain()
//                        case "LOAD" => state.level.loadTerrain()
                        case "COLORPICK" => p.colorpick()
                        case "PRINT" => p.printManualPlacementCode()
                        case _ =>
                }
                case _ =>
            }
    }

    def keyReleased(key: String): Unit = {
        if(!IsoGame.editorMode)
            key match {
                case "UP" => state.player.accelerate(Point3Double(1*Player.walkingSpeed, 1*Player.walkingSpeed, 0))
                case "LEFT" => state.player.accelerate(Point3Double(Player.walkingSpeed, -Player.walkingSpeed, 0))
                case "DOWN" => state.player.accelerate(Point3Double(-1*Player.walkingSpeed, -1*Player.walkingSpeed, 0))
                case "RIGHT" => state.player.accelerate(Point3Double(-Player.walkingSpeed, Player.walkingSpeed, 0))
                case _ =>
            }
    }

}