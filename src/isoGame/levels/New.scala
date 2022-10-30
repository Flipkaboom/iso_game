package isoGame.levels
import isoGame.{IsoGame, Point3, Point3Double}

class New() extends Level {
    var name: String = "New"
    val chunkSize: Point3 = IsoGame.editorSize
    val loadFromFile: Boolean = false
    val playerSpawn: Point3Double = Point3Double(0,0,0)

    def buildLevel(): Unit = {
        name = IsoGame.newLevelName
    }
}
