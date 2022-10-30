package isoGame.levels
import isoGame.{Point3, Point3Double}

class FirstRoom extends Level {
    var name: String = "FirstRoom"
    val chunkSize: Point3 = Point3(8, 8, 16)
    val loadFromFile: Boolean = true
    val playerSpawn: Point3Double = Point3Double(4.5, 4.5, 3)

    def buildLevel(): Unit = {

    }
}
